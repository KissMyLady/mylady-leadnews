package top.mylady.model.mappers.app;
import top.mylady.model.article.pojos.ApArticleConfig;


/**
 * 文章ID查询内容方法
 */
public interface ApArticleConfigMapper {
    ApArticleConfig selectByArticleId(Integer articleId);

    int insertApArticleConfig(ApArticleConfig apArticleConfig);
}

