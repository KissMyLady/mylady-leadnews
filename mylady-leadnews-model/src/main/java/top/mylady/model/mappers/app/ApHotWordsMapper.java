package top.mylady.model.mappers.app;


import top.mylady.model.article.pojos.ApHotWords;

import java.util.List;

/**
 * 今日热词
 */
public interface ApHotWordsMapper {

    /**
     * 今日热词
     */
    List<ApHotWords> queryByHotDate(String hotDate);
}
