package top.mylady.article.service;


import org.apache.coyote.Response;
import top.mylady.model.article.dtos.UserSearchDto;
import top.mylady.model.common.dtos.ResponseResult;

/**
 * 搜索
 */
public interface ApArticleSearchService {

    /**
     * 搜索历史记录
     */
    public ResponseResult findUserSearchHistory(UserSearchDto userSearchDto);

    /**
     * 删除
     */
    ResponseResult delUserSearch(UserSearchDto dto);

    /**
     * 清空搜索记录
     */
    ResponseResult clearUserSearch(UserSearchDto dto);

    /**
     * 今日热词
     */
    ResponseResult hotKeyWords(String date);

    /**
     * 联想词查询
     */
    ResponseResult searchAssociate(UserSearchDto dto);

    /**
     * Elastic文章分页查询
     */
    ResponseResult esArticleSearch(UserSearchDto dto);

    /**
     * 保存历史记录
     */
    ResponseResult saveUserSearchHistory(Integer entryId, String searchWords);
}
