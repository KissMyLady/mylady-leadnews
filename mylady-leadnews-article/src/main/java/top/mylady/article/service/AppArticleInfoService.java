package top.mylady.article.service;
import top.mylady.model.article.dtos.ArticleInfoDto;
import top.mylady.model.common.dtos.ResponseResult;


/**
 * 定义文章详情接口
 */
public interface AppArticleInfoService {

    /**
     * 加载文章详情内容
     */
    ResponseResult getArticleInfo(Integer articleId);


    /**
     * 加载文章的初始化信息, 比如喜欢, 不喜欢, 阅读位置等
     */
    ResponseResult loadArticleBehavior(ArticleInfoDto dtl);
}
