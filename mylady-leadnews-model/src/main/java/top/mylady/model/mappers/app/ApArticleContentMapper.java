package top.mylady.model.mappers.app;
import top.mylady.model.article.pojos.ApArticleContent;


public interface ApArticleContentMapper {
    ApArticleContent selectByArticleId(Integer articleId);

    void insertApArticleContent(ApArticleContent apArticleContent);
}
