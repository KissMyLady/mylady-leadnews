package top.mylady.model.mappers.app;
import top.mylady.model.user.pojos.ApUserFollow;


/**
 * 用户关注信息
 */
public interface ApUserFollowMapper {
    ApUserFollow selectByFollowId(String burst, Long userId, Integer followId);


    /**
     * 用户关注信息
     */
    int insertApUserFollow(ApUserFollow record);

    int deleteByFollowId(String burst, Long userId, Integer followId);
}
