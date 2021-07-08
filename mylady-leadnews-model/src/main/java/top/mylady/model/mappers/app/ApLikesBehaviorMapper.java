package top.mylady.model.mappers.app;
import org.apache.ibatis.annotations.Param;
import top.mylady.model.behavior.pojos.ApLikesBehavior;


/**BugBugBugBugBug
 * BugBugBugBugBug
 */
public interface ApLikesBehaviorMapper {

    /**
     * 选择最后一条喜欢按钮
     * @return 查询结果
     */
    ApLikesBehavior selectLastLike(
            @Param("burst") String burst,
            @Param("objectId") Integer objectId,
            @Param("entryId") Integer entryId,
            @Param("type") Short type
    );

    /**
     * 保存
     * @param record ApLikesBehavior类型
     * @return int
     */
    int insertApLikesBehavior(ApLikesBehavior record);

}
