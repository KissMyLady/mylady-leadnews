package top.mylady.admin.apis;


import top.mylady.model.admin.pojos.AdUser;
import top.mylady.model.common.dtos.ResponseResult;

public interface LoginCtrlApi {

    ResponseResult login(AdUser user);
}
