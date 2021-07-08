package top.mylady.login.service.impl;

import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.mylady.login.service.ApUserLoginService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.common.enums.AppHttpCodeEnum;
import top.mylady.model.mappers.login.LoginUserMapper;
import top.mylady.model.user.pojos.ApUser;
import top.mylady.utils.jwt.AppJwtUtil;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApUserLoginServiceImpl implements ApUserLoginService {

    @Autowired
    private LoginUserMapper loginUserMapper;

    /**
     * 用户登录校验
     */
    @Override
    public ResponseResult loginAuth(ApUser user) {
        //校验参数
        if(StringUtils.isEmpty(user.getPhone()) || StringUtils.isEmpty(user.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        //查询用户
        ApUser apUser = loginUserMapper.selectByApPhone(user.getPhone());

        if (apUser == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.AP_USER_DATA_NOT_EXIST);
        }

        if (!user.getPassword().equals(apUser.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        apUser.setPassword("");
        Map<String, Object> map = new HashMap<>();
        map.put("token", AppJwtUtil.getToken(apUser));
        map.put("user", apUser);
        return ResponseResult.okResult(map);
    }
}
