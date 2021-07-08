package top.mylady.model.mappers.app;

import top.mylady.model.behavior.pojos.ApShowBehavior;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApShowBehaviorMapper {


    /**
     * 获取存在用户的数据
     * @param entryid 文章IDS
     * @param articleIds 实体ID
     * @return 查询列表
     */
    List<ApShowBehavior> selectListByEntryIdAndArticleIds(@Param("entryId") Integer entryid, @Param("articleIds")Integer[] articleIds);

    /**
     * 保存用户展现行为数据
     * @param articleIds 文章IDS
     * @param entryId 实体ID
     * @return null
     */
    void saveShowBehavior(@Param("articleIds") Integer[] articleIds, @Param("entryId") Integer entryId);
}
