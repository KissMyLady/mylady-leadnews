package top.mylady.user.service.impl;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.common.zookeeper.sequence.Sequences;
import top.mylady.model.article.pojos.ApAuthor;
import top.mylady.model.behavior.dtos.FollowBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.ApAuthorMapper;
import top.mylady.model.mappers.app.ApUserFanMapper;
import top.mylady.model.mappers.app.ApUserFollowMapper;
import top.mylady.model.mappers.app.ApUserMapper;
import top.mylady.model.user.dtos.UserRelationDto;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.model.user.pojos.ApUserFan;
import top.mylady.model.user.pojos.ApUserFollow;
import top.mylady.user.service.ApUserRelationService;
import top.mylady.user.service.AppFollowBehaviorService;
import top.mylady.utils.common.BurstUtils;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.Date;


/**
 * 关注用户实现类
 */
@Getter
@Service
public class ApUserRelationServiceImpl implements ApUserRelationService {

    private static final Logger logger = Logger.getLogger(ApUserRelationServiceImpl.class);

    @Autowired
    public ApUserFollowMapper apUserFollowMapper;

    @Autowired
    public ApUserFanMapper apUserFanMapper;

    @Autowired
    public ApAuthorMapper apAuthorMapper;

    @Autowired
    public ApUserMapper apUserMapper;

    @Autowired
    public AppFollowBehaviorService appFollowBehaviorService;

    @Autowired
    public Sequences sequences;

    /**
     * 关注, 取消关注
     */
    @Override
    public ResponseResult followUser(UserRelationDto dto) {
        if(dto.getOperation() == null || dto.getOperation() < 0 || dto.getOperation() > 1){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"无效的operation参数");
        }

        Integer followId = dto.getUserId();

        if(followId == null && dto.getAuthorId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"followId或authorId不能为空");
        }
        else if(followId == null) {
            ApAuthor aa = apAuthorMapper.selectById(dto.getAuthorId());
            if(aa != null) {
                followId = aa.getUserId().intValue();
            }
        }

        if(followId == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"关注人不存在");
        }
        else {
            ApUser user = AppThreadLocalUtils.getUser();
            if(user!=null) {
                if(dto.getOperation()==0) {
                    return followByUserId(user, followId, dto.getArticleId());
                }else{
                    return followCancelByUserId(user,followId);
                }
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            }
        }

    }

    /**
     * 处理关注逻辑
     */
    private ResponseResult followByUserId(ApUser user,Integer followId,Integer articleId){

        // 判断用户是否存在
        ApUser followUser = apUserMapper.selectByUserId(followId);
        if(followUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"关注用户不存在");
        }

        ApUserFollow auf = apUserFollowMapper.selectByFollowId(BurstUtils.groudOne(user.getId()),user.getId(),followId);

        if(auf == null ){
            ApUserFan fan = apUserFanMapper.selectByFansUserId(BurstUtils.groudOne(followId), followId, user.getId());

            if (fan == null) {
                fan = new ApUserFan();
                fan.setId(sequences.sequenceApUserFan());  //Zk生成Id
                fan.setUserId(followId);
                fan.setFansId(user.getId());
                fan.setFansName(user.getName());
                fan.setLevel((short) 0);
                fan.setIsDisplay(true);
                fan.setIsShieldComment(false);
                fan.setIsShieldLetter(false);
                fan.setBurst(BurstUtils.encrypt(fan.getId(), fan.getUserId()));
                apUserFanMapper.insertApUserFan(fan);
            }

            auf = new ApUserFollow();
            auf.setId(sequences.sequenceApUserFollow());  //Zk生成Id
            auf.setUserId(user.getId());
            auf.setFollowId(followId);
            auf.setFollowName(followUser.getName());
            auf.setCreatedTime(new Date());
            auf.setLevel((short) 0);
            auf.setIsNotice(true);
            auf.setBurst(BurstUtils.encrypt(auf.getId(), auf.getUserId()));

            // 记录关注行为
            FollowBehaviorDto dto = new FollowBehaviorDto();
            dto.setFollowId(followId);
            dto.setArticleId(articleId);
            appFollowBehaviorService.saveFollowBehavior(dto);
            logger.debug("关注api, 记录关注行为");
            return ResponseResult.okResult(apUserFollowMapper.insertApUserFollow(auf));
        } else{
            logger.debug("关注api, 已经关注");
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"已关注");
        }
    }

    /**
     * 处理取消关注逻辑
     */
    private ResponseResult followCancelByUserId(ApUser user, Integer followId){

        //从ap_user_follow表, 查询是否存在[user_id, follow_id] 的关注信息
        ApUserFollow appUserFollowMapper = apUserFollowMapper.selectByFollowId(
                BurstUtils.groudOne(user.getId()), user.getId(), followId
        );

        if(appUserFollowMapper == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"未关注");
        }
        else{
            //从ap_user_fan表, 查询[user_id, fans_id] 的粉丝
            ApUserFan apUserFan = apUserFanMapper.selectByFansUserId(
                    BurstUtils.groudOne(followId),
                    followId, user.getId());

            if (apUserFan != null) {
                //存在这个样的关注, 删除
                apUserFanMapper.deleteApUserFan(BurstUtils.groudOne(followId), followId, user.getId());
            }

            //返回删除成功的标志
            return ResponseResult.okResult(apUserFollowMapper.deleteByFollowId(BurstUtils.groudOne(user.getId()), user.getId(), followId));
        }
    }

}
