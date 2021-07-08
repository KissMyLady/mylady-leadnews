package top.mylady.login.apis;
import org.springframework.web.bind.annotation.RequestBody;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.user.pojos.ApUser;


/**
 * 路由Api
 */
public interface LoginCtrlApi {

    public ResponseResult login(@RequestBody ApUser user);
}
