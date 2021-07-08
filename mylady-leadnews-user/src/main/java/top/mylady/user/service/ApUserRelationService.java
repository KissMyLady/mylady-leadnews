package top.mylady.user.service;


import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.dtos.UserRelationDto;


/**
 * 关注
 */
public interface ApUserRelationService {

    public ResponseResult followUser(UserRelationDto dto);
}
