package top.mylady.user.service;

import top.mylady.model.behavior.dtos.FollowBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;


public interface AppFollowBehaviorService {

    /**
     * 存储关注信息
     */
    public ResponseResult saveFollowBehavior(FollowBehaviorDto dto);
}
