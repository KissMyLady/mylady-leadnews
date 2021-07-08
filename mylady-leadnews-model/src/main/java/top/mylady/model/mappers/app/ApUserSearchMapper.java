package top.mylady.model.mappers.app;


import org.apache.ibatis.annotations.Param;
import top.mylady.model.user.pojos.ApUserSearch;

import java.util.List;

/**
 * 用户搜索
 */
public interface ApUserSearchMapper {

    /**
     * 搜索历史记录
     */
    public List<ApUserSearch> selectSearchHistory(
            @Param("entryId") Integer enteryId,
            @Param("limit") int limit
    );

    /**
     * 删除
     */
    int deleteUserSearchHistory(
            @Param("entryId") Integer entryID,
            @Param("hisIds") List<Integer> hisIds
    );

    /**
     * 清空搜索记录
     */
    int clearUserSearch(Integer entryId);

    /**
     * 插入搜索历史记录
     */
    int insertSearchHistory(ApUserSearch record);

    /**
     * 定义检查记录是否存在
     */
    int checkExistSearchHistory(
            @Param("entryId") Integer entryId,
            @Param("keyword") String keyword
    );
}
