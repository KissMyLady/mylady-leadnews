package top.mylady.model.mappers.login;


import top.mylady.model.user.pojos.ApUser;

/**
 * 登录
 */
public interface LoginUserMapper {
    ApUser selectByApPhone(String phone);
}
