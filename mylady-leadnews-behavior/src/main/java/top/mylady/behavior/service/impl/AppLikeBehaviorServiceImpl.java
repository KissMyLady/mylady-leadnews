package top.mylady.behavior.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.behavior.service.AppLikeBehaviorService;
import top.mylady.common.zookeeper.sequence.Sequences;
import top.mylady.model.behavior.dtos.LikesBehaviorDto;
import top.mylady.model.behavior.dtos.UnLikesBehaviorDto;
import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import top.mylady.model.behavior.pojos.ApLikesBehavior;
import top.mylady.model.behavior.pojos.ApUnlikesBehavior;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.ApBehaviorEntryMapper;
import top.mylady.model.mappers.app.ApLikesBehaviorMapper;
import top.mylady.model.mappers.app.ApUnlikesBehaviorMapper;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.utils.common.BurstUtils;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;
import java.util.Date;


/**
 * 不喜欢, 喜欢
 */
@Service
public class AppLikeBehaviorServiceImpl implements AppLikeBehaviorService {

    private static final Logger logger = Logger.getLogger(AppLikeBehaviorServiceImpl.class);
    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private Sequences sequences;

    /**
     * 点赞
     */
    @Override
    public ResponseResult saveLikeBehavior(LikesBehaviorDto dto) {
        ApUser user = AppThreadLocalUtils.getUser();

        //用户和设备不能同时为空
        if (user == null && dto.getEquipmentId() == null){
            logger.warn("Behavior: 用户和设备不能同时为空");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        Long userId = null;

        if (user != null){
            userId = user.getId();
        }

        //查询数据库, 两个参数有一个即可
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId() );

        //行为实体已经注册, 落基山这里是必定有值, 除非参数错误
        if (apBehaviorEntry == null){
            logger.warn("Behavior: 行为实体已经注册, 落基山这里是必定有值, 除非参数错误");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        ApLikesBehavior apLikesBehavior = new ApLikesBehavior();
        Long apLikeId = sequences.sequenceApLikes();
        logger.debug("打印Zk生成的apLikeId: "+ apLikeId);

        apLikesBehavior.setId(apLikeId);
        apLikesBehavior.setBehaviorEntryId(apBehaviorEntry.getId());
        apLikesBehavior.setCreatedTime(new Date());

        apLikesBehavior.setEntryId(dto.getEntryId());
        apLikesBehavior.setType(dto.getType());
        apLikesBehavior.setOperation(dto.getOperation());
        apLikesBehavior.setBurst(
                //使用 - 连接连个对象
                BurstUtils.encrypt(
                        apLikesBehavior.getId(), apLikesBehavior.getBehaviorEntryId()
                )
        );

        return ResponseResult.okResult(apLikesBehaviorMapper.insertApLikesBehavior(apLikesBehavior));
    }


    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;

    /**
     * 不喜欢
     */
    @Override
    public ResponseResult saveUnLikeBehavior(UnLikesBehaviorDto dto) {

        //获取用户信息，获取设备id
        ApUser user = AppThreadLocalUtils.getUser();

        //根据当前的用户信息或设备id查询行为实体 ap_behavior_entry
        if(user == null && dto.getEquipmentId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        Long userId = null;

        if(user != null){
            userId = user.getId();
        }
        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(
                userId, dto.getEquipmentId()
        );

        if(apBehaviorEntry == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        logger.debug("Behavior: 不喜欢service, apBehaviorEntry不为空, 开始保存");

        ApUnlikesBehavior alb = new ApUnlikesBehavior();
        //alb.setId(sequences.sequenceApLikes());
        alb.setEntryId(apBehaviorEntry.getId());
        alb.setCreatedTime(new Date());
        alb.setArticleId(dto.getArticleId());
        alb.setType(dto.getType());
        logger.debug("Behavior: 不喜欢service, 打印设置的ApUnlikesBehavior对象: "+ alb);

        int insert = 0;

        try {
            insert = apUnlikesBehaviorMapper.insertDisLike(alb);
        }catch (Exception e){
            logger.error("Behavior: 不喜欢|取消不喜欢, 数据库插入错误 \r\n");
            logger.error(String.format("数据库写入不喜欢错误, 原因是: %s ", e));
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        return ResponseResult.okResult(insert);
    }
}
