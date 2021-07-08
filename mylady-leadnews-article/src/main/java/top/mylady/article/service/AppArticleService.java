package top.mylady.article.service;

import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.common.dtos.ResponseResult;


/**
 * 服务层
 */
public interface AppArticleService {

    /**
     * @param type 1, 加载更多, 2 加载更新
     * @param dto 封装数据
     * @return 数据列表
     */
    public abstract ResponseResult load(Short type, ArticleHomeDto dto);

}
