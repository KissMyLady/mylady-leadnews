package top.mylady.media.apis;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.pojos.WmUser;


public interface LoginControllerApi {

    public ResponseResult login(WmUser user);
}
