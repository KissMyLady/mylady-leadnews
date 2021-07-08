package top.mylady.utils.threadlocal;

import top.mylady.model.user.pojos.ApUser;


/**
 * 把当前app用户, 给放进去
 */
public class AppThreadLocalUtils {

    private final static ThreadLocal<ApUser> userThreadLocal = new ThreadLocal<>();

    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(ApUser user){
        userThreadLocal.set(user);
    }

    /**
     * 获取线程中的用户
     * @return
     */
    public static ApUser getUser( ){
        return userThreadLocal.get();
    }

}
