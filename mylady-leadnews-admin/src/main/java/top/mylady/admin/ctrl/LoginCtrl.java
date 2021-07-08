package top.mylady.admin.ctrl;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.admin.apis.LoginCtrlApi;
import top.mylady.admin.service.AdminUserLogin;
import top.mylady.model.admin.pojos.AdUser;
import top.mylady.model.common.dtos.ResponseResult;


@RestController
@RequestMapping("/login")
public class LoginCtrl implements LoginCtrlApi {

    private static final Logger logger = Logger.getLogger(LoginCtrl.class);

    @Autowired
    private AdminUserLogin adminUserLogin;

    @Override
    @RequestMapping("/in")
    public ResponseResult login(@RequestBody AdUser user) {
        return adminUserLogin.login(user);
    }
}
