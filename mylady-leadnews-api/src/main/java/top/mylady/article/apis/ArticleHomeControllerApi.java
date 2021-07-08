package top.mylady.article.apis;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.article.dtos.ArticleHomeDto;


/**
 * 首页文章
 */
public interface ArticleHomeControllerApi {
    /**
     * 加载首页文章
     * @param dto 封装参数对象
     * @return 文章列表数据
     */
    public abstract ResponseResult load(ArticleHomeDto dto);

    /**
     * 加载更多
     * @param dot 封装参数对象
     * @return 文章列表数据
     */
    public abstract ResponseResult loadMore(ArticleHomeDto dot);

    /**
     * 加载最新的文章信息
     * @param dto 参数封装数据
     * @return 文章列表
     */
    public abstract ResponseResult loadNew(ArticleHomeDto dto);
}
