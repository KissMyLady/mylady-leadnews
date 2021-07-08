package top.mylady.login.ctrl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.login.apis.LoginCtrlApi;
import top.mylady.login.service.ApUserLoginService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.pojos.ApUser;


/**
 * 登录
 */
@RestController
@RequestMapping("/ap1/v1/login")
public class LoginCtrl implements LoginCtrlApi {

    @Autowired
    private ApUserLoginService apUserLoginService;

    @Override
    @RequestMapping("/login_auth")
    public ResponseResult login(@RequestBody ApUser user) {
        return apUserLoginService.loginAuth(user);
    }
}
