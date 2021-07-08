package top.mylady.login.service;

import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.pojos.ApUser;


public interface ApUserLoginService {

    /**
     * 用户登录验证
     */
    ResponseResult loginAuth(ApUser apUser);
}
