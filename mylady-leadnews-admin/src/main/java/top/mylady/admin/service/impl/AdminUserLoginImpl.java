package top.mylady.admin.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.admin.service.AdminUserLogin;
import top.mylady.model.admin.pojos.AdUser;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.admin.AdUserMapper;
import top.mylady.utils.jwt.AppJwtUtil;

import java.util.HashMap;
import java.util.Map;


/**
 *
 */
@Service
public class AdminUserLoginImpl implements AdminUserLogin {

    private static final Logger logger = Logger.getLogger(AdminUserLoginImpl.class);

    @Autowired
    private AdUserMapper adUserMapper;

    @Override
    public ResponseResult login(AdUser user) {
        if(StringUtils.isEmpty(user.getName())&&StringUtils.isEmpty(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"用户和密码不能为空");
        }

        AdUser adUser = adUserMapper.selectByName(user.getName());

        if(adUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"用户不存在");
        }else{
            if(user.getPassword().equals(adUser.getPassword())){
                Map<String,Object> map = new HashMap<>();
                adUser.setPassword("");
                adUser.setSalt("");
                map.put("token", AppJwtUtil.getToken(adUser));
                map.put("user",adUser);
                return ResponseResult.okResult(map);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
            }
        }


    }
}
