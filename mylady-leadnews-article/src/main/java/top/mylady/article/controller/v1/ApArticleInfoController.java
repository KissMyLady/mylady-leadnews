package top.mylady.article.controller.v1;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.article.apis.ArticleInfoControllerApi;
import top.mylady.article.service.AppArticleInfoService;
import top.mylady.model.article.dtos.ArticleInfoDto;
import top.mylady.model.common.dtos.ResponseResult;


@RestController
@RequestMapping("/api/v1/article")
public class ApArticleInfoController implements ArticleInfoControllerApi {

    private static final Logger logger = Logger.getLogger(ApArticleInfoController.class);

    @Autowired
    private AppArticleInfoService appArticleInfoService;

    @PostMapping("/load_article_info")
    @Override
    public ResponseResult loadArticleInfo(@RequestBody ArticleInfoDto dto) {
        return appArticleInfoService.getArticleInfo(dto.getArticleId());
    }

    /**
     * 加载文章行为内容
     */
    @PostMapping("/load_article_behavior")
    @Override
    public ResponseResult loadArticleBehavior(@RequestBody ArticleInfoDto dto) {
        return appArticleInfoService.loadArticleBehavior(dto);
    }
}
