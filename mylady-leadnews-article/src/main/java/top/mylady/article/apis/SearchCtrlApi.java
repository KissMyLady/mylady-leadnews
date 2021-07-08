package top.mylady.article.apis;


import top.mylady.model.article.dtos.UserSearchDto;
import top.mylady.model.common.dtos.ResponseResult;

/**
 * 搜索
 */
public interface SearchCtrlApi {

    /**
     * 搜索记录
     */
    public ResponseResult findUserSearchHistory(UserSearchDto dto);

    /**
     * 删除历史记录
     */
    public ResponseResult deleteUserSearchHistory(UserSearchDto dto);

    /**
     * 清空
     */
    ResponseResult clearUserSearchApi(UserSearchDto dto);

    /**
     * 今日热词
     */
    ResponseResult hotKeyWords(UserSearchDto dto);

    /**
     * 联想词查询
     */
    ResponseResult searchAssociate(UserSearchDto dto);

    /**
     * ElasticSearch 文章分页搜索
     */
    ResponseResult esArticleSearch(UserSearchDto dto);
}
