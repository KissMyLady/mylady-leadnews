package top.mylady.media.service.impl;

import org.apache.log4j.Logger;
import top.mylady.media.service.UserLoginService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.wemedia.WmUserMapper;
import top.mylady.model.media.pojos.WmUser;
import top.mylady.utils.jwt.AppJwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


//@SuppressWarnings("all")
@Service
public class UserLoginServiceImpl implements UserLoginService {

    private static final Logger logger = Logger.getLogger(UserLoginServiceImpl.class);

    @Autowired
    private WmUserMapper wmUserMapper;

    @Override
    public ResponseResult login(WmUser user) {
        if(StringUtils.isEmpty(user.getName())&&StringUtils.isEmpty(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"用户名或密码不能为空");
        }
        WmUser wmUser = wmUserMapper.selectByName(user.getName());
        if(wmUser!=null){
            if(user.getPassword().equals(wmUser.getPassword())){
                Map<String,Object> result = new HashMap<>();
                wmUser.setPassword("");
                wmUser.setSalt("");
                result.put("token", AppJwtUtil.getToken(wmUser));
                result.put("user",wmUser);
                return ResponseResult.okResult(result);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }
    }
}
