package top.mylady.model.article.pojos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


@Data
public class ApArticleContent {
    private Integer id;

    // 增加注解，JSON序列化时自动混淆加密
    @IdEncrypt
    private Integer articleId;

    private String content;

}