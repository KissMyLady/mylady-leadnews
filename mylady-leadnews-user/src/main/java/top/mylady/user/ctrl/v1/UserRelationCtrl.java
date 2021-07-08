package top.mylady.user.ctrl.v1;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.dtos.UserRelationDto;
import top.mylady.user.apis.UserRelationCtrlApi;
import top.mylady.user.service.ApUserRelationService;


@RestController
@RequestMapping("/api/v1/user")
public class UserRelationCtrl implements UserRelationCtrlApi {

    private static final Logger logger = Logger.getLogger(UserRelationCtrl.class);

    @Autowired
    private ApUserRelationService apUserRelationService;


    @PostMapping("/user_follow")
    @Override
    public ResponseResult followUser(@RequestBody UserRelationDto dto) {
        return apUserRelationService.followUser(dto);
    }
}
