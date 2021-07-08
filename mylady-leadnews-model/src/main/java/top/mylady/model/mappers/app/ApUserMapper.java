package top.mylady.model.mappers.app;


import top.mylady.model.user.pojos.ApUser;

/**
 * 用户映射
 */
public interface ApUserMapper {
    ApUser selectByUserId(Integer id);
}
