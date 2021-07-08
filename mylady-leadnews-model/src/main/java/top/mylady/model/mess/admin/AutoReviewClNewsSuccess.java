package top.mylady.model.mess.admin;
import top.mylady.model.article.pojos.ApArticleConfig;
import top.mylady.model.article.pojos.ApArticleContent;
import top.mylady.model.article.pojos.ApAuthor;
import lombok.Data;


@Data
public class AutoReviewClNewsSuccess {
    private ApArticleConfig apArticleConfig;
    private ApArticleContent apArticleContent;
    private ApAuthor apAuthor;

}
