package top.mylady.behavior.service;


import top.mylady.model.behavior.dtos.LikesBehaviorDto;
import top.mylady.model.behavior.dtos.UnLikesBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;

public interface AppLikeBehaviorService {

    /**
     * 点赞
     */
    public ResponseResult saveLikeBehavior(LikesBehaviorDto dto);

    /**
     * 不喜欢
     */
    public ResponseResult saveUnLikeBehavior(UnLikesBehaviorDto dto);
}
