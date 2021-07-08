package top.mylady.article.controller.v1;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.article.apis.SearchCtrlApi;
import top.mylady.article.service.ApArticleSearchService;
import top.mylady.model.article.dtos.UserSearchDto;
import top.mylady.model.common.dtos.ResponseResult;


@RestController
@RequestMapping("/api/v1/article/search")
public class SearchCtrlImpl implements SearchCtrlApi {

    private static final Logger logger = Logger.getLogger(SearchCtrlImpl.class);

    @Autowired
    private ApArticleSearchService apArticleSearchService;

    /**
     * 搜索历史记录
     */
    @Override
    @PostMapping("/load_search_history")
    public ResponseResult findUserSearchHistory(@RequestBody UserSearchDto dto) {
        logger.debug("Search ctrl路由层, 打印前端传递过来的UserSearchDto dto: "+ dto);
        return apArticleSearchService.findUserSearchHistory(dto);
    }

    /**
     * 删除历史记录
     */
    @Override
    @PostMapping("/del_search")
    public ResponseResult deleteUserSearchHistory(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.delUserSearch(dto);
    }

    /**
     * 清空
     */
    @Override
    @PostMapping("/clear_search")
    public ResponseResult clearUserSearchApi(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.clearUserSearch(dto);
    }

    /**
     * 今日热词
     */
    @Override
    @PostMapping("/load_hot_keywords")
    public ResponseResult hotKeyWords(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.hotKeyWords(dto.getHotDate());
    }

    /**
     * 联想词查询
     */
    @Override
    @PostMapping("/associate_search")
    public ResponseResult searchAssociate(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.searchAssociate(dto);
    }

    /**
     * 文章搜索
     */
    @Override
    @PostMapping("/article_search")
    public ResponseResult esArticleSearch(@RequestBody UserSearchDto dto) {
        return apArticleSearchService.esArticleSearch(dto);
    }
}
