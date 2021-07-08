package top.mylady.model.mappers.app;


import top.mylady.model.user.pojos.ApUserFan;

/**
 * 用户粉丝信息
 *  定义插入, 查找, 删除粉丝的方法
 */
public interface ApUserFanMapper {

    //插入
    int insertApUserFan(ApUserFan record);

    //删除
    int deleteApUserFan(String burst, Integer userId, Long fansId);

    //查找
    ApUserFan selectByFansUserId(String burst, Integer userId, Long fansId);

}
