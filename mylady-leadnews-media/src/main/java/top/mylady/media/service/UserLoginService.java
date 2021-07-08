package top.mylady.media.service;

import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.pojos.WmUser;

public interface UserLoginService {

    /**
     * 登录方法
     */
    public ResponseResult login(WmUser user);
}
