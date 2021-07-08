package top.mylady.user.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import top.mylady.common.zookeeper.sequence.Sequences;
import top.mylady.model.behavior.dtos.FollowBehaviorDto;
import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import top.mylady.model.behavior.pojos.ApFollowBehavior;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.ApBehaviorEntryMapper;
import top.mylady.model.mappers.app.ApFollowBehaviorMapper;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.user.service.AppFollowBehaviorService;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;


/**
 * 异步关注接口
 */
@Service
@Async
public class AppFollowBehaviorServiceImpl implements AppFollowBehaviorService {

    private static final Logger logger = Logger.getLogger(AppFollowBehaviorServiceImpl.class);

    @Autowired
    private ApFollowBehaviorMapper apFollowBehaviorMapper;

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private Sequences sequences;


    @Override
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto) {
        logger.info("关注接口调用, 异步执行...");

        ApUser user = AppThreadLocalUtils.getUser();

        // 用户和设备不能同时为空
        if(user == null && dto.getEquipmentId() == null){
            logger.warn("关注API, 用户和设备不能同时为空");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;

        if(user != null){
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(
                userId, dto.getEquipmentId()
        );

        // 行为实体找以及注册了, 逻辑上这里是必定有值得, 除非参数错误
        if(apBehaviorEntry == null){
            logger.warn("关注API, 行为实体找以及注册了, 逻辑上这里是必定有值得, 除非参数错误");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApFollowBehavior alb = new ApFollowBehavior();

        alb.setId(sequences.sequenceApReadBehavior());  //Zk生成id
        alb.setEntryId(apBehaviorEntry.getId());
        alb.setCreatedTime(new Date());
        alb.setArticleId(dto.getArticleId());
        alb.setFollowId(dto.getFollowId());

        logger.debug("异步关注成功, 返回相应信息");
        return ResponseResult.okResult(apFollowBehaviorMapper.insertFollowBehavior(alb));

    }
}
