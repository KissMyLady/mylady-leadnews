package top.mylady.model.mess.admin;

import lombok.Data;


/**
 * 封装传递的消息, 并区分消息的类型
 */
@Data
public class SubmitArticleAuto {

    // 文章类型
    private ArticleType type;

    // 文章ID
    private Integer articleId;

    //weMedia  Crawler 爬虫
    public enum ArticleType{
        WEMEDIA, CRAWLER;
    }

}
