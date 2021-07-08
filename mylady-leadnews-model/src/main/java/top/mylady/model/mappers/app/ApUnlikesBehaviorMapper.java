package top.mylady.model.mappers.app;
import top.mylady.model.behavior.pojos.ApUnlikesBehavior;


public interface ApUnlikesBehaviorMapper {

    /**
     * 选择最后一条不喜欢数据
     */
    ApUnlikesBehavior selectLastUnLike(Integer entryId, Integer articleId);

    /**
     * 不喜欢
     */
    int insertDisLike(ApUnlikesBehavior record);

}
