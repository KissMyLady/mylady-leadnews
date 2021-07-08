package top.mylady.media.ctrl.v1;

import top.mylady.media.apis.LoginControllerApi;
import top.mylady.media.service.UserLoginService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.pojos.WmUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController implements LoginControllerApi {

    @Autowired
    private UserLoginService userLoginService;

    @Override
    @RequestMapping("/in")
    public ResponseResult login(@RequestBody WmUser user) {
        return userLoginService.login(user);
    }
}
