package top.mylady.user.apis;


import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.dtos.UserRelationDto;


public interface UserRelationCtrlApi {

    ResponseResult followUser(UserRelationDto dto);
}
