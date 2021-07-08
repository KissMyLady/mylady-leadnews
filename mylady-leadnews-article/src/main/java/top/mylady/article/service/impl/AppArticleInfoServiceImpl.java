package top.mylady.article.service.impl;

import lombok.Getter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.article.service.AppArticleInfoService;
import top.mylady.model.article.dtos.ArticleInfoDto;
import top.mylady.model.article.pojos.ApArticleConfig;
import top.mylady.model.article.pojos.ApArticleContent;
import top.mylady.model.article.pojos.ApAuthor;
import top.mylady.model.article.pojos.ApCollection;
import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import top.mylady.model.behavior.pojos.ApLikesBehavior;
import top.mylady.model.behavior.pojos.ApUnlikesBehavior;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.app.*;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.model.user.pojos.ApUserFollow;
import top.mylady.utils.common.BurstUtils;
import top.mylady.utils.threadlocal.AppThreadLocalUtils;

import java.util.HashMap;
import java.util.Map;


@Getter //lombok 提供get方法
@Service
public class AppArticleInfoServiceImpl implements AppArticleInfoService {

    private static final Logger logger = Logger.getLogger(AppArticleInfoServiceImpl.class);

    @Autowired
    private ApArticleContentMapper apArticleContentMapper;

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Override
    public ResponseResult getArticleInfo(Integer articleId) {

        logger.info(String.format("加载文章详情信息, 打印传递过来的参数articleId: %s ", articleId));

        // 参数无效
        if(articleId == null || articleId < 1){
            logger.warn("加载文章详细, 传递过来的id值错误, 返回"+ AppHttpCodeEnum.PARAM_INVALID);
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApArticleConfig config = apArticleConfigMapper.selectByArticleId(articleId);
        Map<String, Object> data = new HashMap<>();

        // 参数无效
        if(config == null){
            logger.warn("当前config参数无效");
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }else if(!config.getIsDelete()){
            // 没删除的标识才返回给客户端
            ApArticleContent content = apArticleContentMapper.selectByArticleId(articleId);
            data.put("content", content);
        }

        data.put("config", config);
        logger.debug("文章详情, 完成返回信息的组装, 返回200的相应信息");
        return ResponseResult.okResult(data);
    }

    @Autowired
    private ApBehaviorEntryMapper apBehaviorEntryMapper;

    @Autowired
    private ApCollectionMapper apCollectionMapper;

    @Autowired
    private ApLikesBehaviorMapper apLikesBehaviorMapper;

    @Autowired
    private ApUnlikesBehaviorMapper apUnlikesBehaviorMapper;

    @Autowired
    private ApAuthorMapper apAuthorMapper;

    @Autowired
    private ApUserFollowMapper apUserFollowMapper;

    /**
     * 加载文章的配置, 喜欢, 不喜欢, 阅读位置等[文章行为]
     */
    @Override
    public ResponseResult loadArticleBehavior(ArticleInfoDto dto) {

        ApUser user = AppThreadLocalUtils.getUser();
        logger.debug("加载文章的喜欢与阅读配置信息, 打印传递过来的dto: "+ dto);

        //用户与设备不能同时为空
        if(user==null && dto.getEquipmentId() == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }

        //1，通过equipmentId或用户id查看行为实体  --->entryId
        Long userId = null;
        if(user != null){
            userId = user.getId();
        }

        ApBehaviorEntry apBehaviorEntry = apBehaviorEntryMapper.selectByUserIdOrEquipment(userId, dto.getEquipmentId());

        boolean isUnlike = false,
                isLike = false,
                isCollection = false,
                isFollow=false;

        String burst = BurstUtils.groudOne(apBehaviorEntry.getId());

        //2，通过entryId和articleId去查看收藏表，看是否有数据，有数据就说明已经收藏，否则就是没有收藏
        ApCollection apCollection = apCollectionMapper.selectForEntryId(
                burst, apBehaviorEntry.getId(), dto.getArticleId(),
                ApCollection.Type.ARTICLE.getCode()
        );
        if(apCollection!=null){
            isCollection=true;
        }

        // 3，通过entryId和articleId去查看点赞表，看是否有数据，有数据就说明已经点赞，否则就是没有点赞
        ApLikesBehavior apLikesBehavior = apLikesBehaviorMapper.selectLastLike(
                burst, apBehaviorEntry.getId(),
                dto.getArticleId(), ApCollection.Type.ARTICLE.getCode()
        );

        if(apLikesBehavior!=null && apLikesBehavior.getOperation() == ApLikesBehavior.Operation.LIKE.getCode()){
            isLike = true;
        }

        //4，通过entryId和articleId去查看不喜欢表，看是否有数据，有数据就说明不喜欢，否则就是喜欢
        ApUnlikesBehavior apUnlikesBehavior = apUnlikesBehaviorMapper.selectLastUnLike(
                apBehaviorEntry.getId(), dto.getArticleId()
        );

        if(apUnlikesBehavior != null && apUnlikesBehavior.getType() == ApUnlikesBehavior.Type.UNLIKE.getCode()){
            isUnlike = true;
        }

        //5，通过authorId去app的id  userId(app账号信息id)
        ApAuthor apAuthor = apAuthorMapper.selectById(dto.getAuthorId());

        //查看关注表，根据当前登录人的userId与作者的app账号id去查询，如果有数据，就说明已经关注，没有则说明没有关注
        if(user!=null && apAuthor != null && apAuthor.getUserId() != null){
            ApUserFollow apUserFollow = apUserFollowMapper.selectByFollowId(
                    BurstUtils.groudOne(user.getId()),
                    user.getId(), apAuthor.getUserId().intValue()
            );

            if(apUserFollow!=null){
                isFollow=true;
            }
        }

        Map<String,Object> data = new HashMap<>();
        data.put("isfollow", isFollow);
        data.put("islike", isLike);
        data.put("isunlike", isUnlike);
        data.put("iscollection", isCollection);

        logger.debug("信封装完毕, 返回信息");
        return ResponseResult.okResult(data);
    }
}
