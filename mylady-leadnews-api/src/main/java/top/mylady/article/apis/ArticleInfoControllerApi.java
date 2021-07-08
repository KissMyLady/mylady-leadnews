package top.mylady.article.apis;

import top.mylady.model.article.dtos.ArticleInfoDto;
import top.mylady.model.common.dtos.ResponseResult;

/**
 * 首页文章
 */
public interface ArticleInfoControllerApi {

    /*
    * 加载首页详情
    * */
    ResponseResult loadArticleInfo(ArticleInfoDto dto);

    /*
    * 加载文章详情行为内容
    * */
    ResponseResult loadArticleBehavior(ArticleInfoDto dto);
}
