package top.mylady.article.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.mylady.article.apis.ArticleHomeControllerApi;
import top.mylady.article.service.AppArticleService;
import top.mylady.common.article.constans.ArticleConstans;
import top.mylady.model.article.dtos.ArticleHomeDto;
import top.mylady.model.common.dtos.ResponseResult;


@RestController
@RequestMapping("/api/v1/article")
public class ArticleIndexCtrl implements ArticleHomeControllerApi{

    @Autowired
    private AppArticleService articleIndexService;

    @Override
    @GetMapping("/load")
    public ResponseResult load(ArticleHomeDto dto) {
        //ArticleConstans.LOADTYPE_LOAD_MORE 常量封装在common下
        return articleIndexService.load(ArticleConstans.LOADTYPE_LOAD_MORE, dto);
    }

    @Override
    @GetMapping("/loadMore")
    public ResponseResult loadMore(ArticleHomeDto dto) {
        return articleIndexService.load(ArticleConstans.LOADTYPE_LOAD_MORE, dto);
    }

    @Override
    @GetMapping("/loadNew")
    public ResponseResult loadNew(ArticleHomeDto dto) {
        return articleIndexService.load(ArticleConstans.LOADTYPE_LOAD_NEW, dto);
    }

    @GetMapping(value="/hello")
    @ResponseBody
    public String getTest(){
        return "Hello leadNews";
    }
}
