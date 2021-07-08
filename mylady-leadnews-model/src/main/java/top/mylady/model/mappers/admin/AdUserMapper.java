package top.mylady.model.mappers.admin;
import top.mylady.model.admin.pojos.AdUser;


/**
 * admin登录Mapper
 */
public interface AdUserMapper {

    /**
     * 名字查询
     */
    AdUser selectByName(String name);
}
