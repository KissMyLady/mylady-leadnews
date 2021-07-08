package top.mylady.model.mappers.app;

import top.mylady.model.article.pojos.ApAuthor;

/**
 * 作者信息
 */
public interface ApAuthorMapper {

    ApAuthor selectById(Integer id);

    ApAuthor selectByAuthorName(String authorName);

    void insertApAuthor(ApAuthor apAuthor);
}
