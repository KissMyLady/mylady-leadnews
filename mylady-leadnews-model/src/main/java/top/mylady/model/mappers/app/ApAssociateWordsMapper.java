package top.mylady.model.mappers.app;


import org.apache.ibatis.annotations.Param;
import top.mylady.model.article.pojos.ApAssociateWords;

import java.util.List;

/**
 * 联想词
 */
public interface ApAssociateWordsMapper {

    /**
     * 根据关键词查询联想词
     */
    List<ApAssociateWords> selectByAssociateWords(
            @Param("searchWords") String searchWords,
            @Param("limit") int limit
    );
}
