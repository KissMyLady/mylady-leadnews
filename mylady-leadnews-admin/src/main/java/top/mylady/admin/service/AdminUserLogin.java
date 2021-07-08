package top.mylady.admin.service;


import top.mylady.model.admin.pojos.AdUser;
import top.mylady.model.common.dtos.ResponseResult;


public interface AdminUserLogin {

    /**
     * 登录
     */
    ResponseResult login(AdUser adUser);
}
