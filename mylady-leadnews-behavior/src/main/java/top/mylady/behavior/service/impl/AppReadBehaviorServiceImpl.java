package top.mylady.behavior.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.behavior.service.AppReadBehaviorService;
import top.mylady.common.zookeeper.sequence.Sequences;
import top.mylady.model.behavior.dtos.ReadBehaviorDto;
import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import top.mylady.model.behavior.pojos.ApReadBehavior;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.ApBehaviorEntryMapper;
import top.mylady.model.mappers.app.ApReadBehaviorMapper;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.utils.common.BurstUtils;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;


/**
 * 阅读行为, 记录阅读时间, 阅读百分比
 */
@Service
public class AppReadBehaviorServiceImpl implements AppReadBehaviorService {

    private static final Logger logger = Logger.getLogger(AppReadBehaviorServiceImpl.class);

    @Autowired
    private ApReadBehaviorMapper apReadBehaviorMapper;

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private Sequences sequences;

    @Override
    public ResponseResult saveReadBehavior(ReadBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();

        // 用户和设备不能同时为空
        if(user == null && dto.getEquipmentId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        Long userId = null;

        if(user != null){
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId());

        // 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误
        if(apBehaviorEntry == null){
            logger.warn("Behavior Read行为记录: 行为实体找以及注册了，逻辑上这里是必定有值得，除非参数错误");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApReadBehavior alb = apReadBehaviorMapper.selectReadBehavior(
                //将多个对象拼接
                BurstUtils.groudOne(
                        apBehaviorEntry.getId()), apBehaviorEntry.getId(), dto.getArticleId()
        );

        boolean isInsert = false;

        if(alb == null){
            alb = new ApReadBehavior();
            alb.setId(sequences.sequenceApReadBehavior());
            isInsert = true;
        }
        alb.setEntryId(apBehaviorEntry.getId());
        alb.setCount(dto.getCount());
        alb.setPercentage(dto.getPercentage());
        alb.setArticleId(dto.getArticleId());
        alb.setLoadDuration(dto.getLoadDuration());
        alb.setReadDuration(dto.getReadDuration());
        Date date = new Date();
        alb.setCreatedTime(date);
        alb.setUpdatedTime(date);

        alb.setBurst(
                //分片桶字段算法
                BurstUtils.encrypt(alb.getId(), alb.getEntryId()));

        // 插入
        if(isInsert){
            return ResponseResult.okResult(apReadBehaviorMapper.insertReadBehavior(alb));
        }else {
            // 更新
            return ResponseResult.okResult(apReadBehaviorMapper.updataReadBehavior(alb));
        }

    }
}
