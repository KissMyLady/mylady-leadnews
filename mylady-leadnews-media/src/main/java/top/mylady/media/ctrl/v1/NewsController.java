package top.mylady.media.ctrl.v1;

import top.mylady.common.media.constans.WmMediaConstans;
import top.mylady.media.apis.NewsControllerApi;
import top.mylady.media.service.NewsService;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.dtos.WmNewsDto;
import top.mylady.model.media.dtos.WmNewsPageReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/media/news")
public class NewsController implements NewsControllerApi {

    @Autowired
    private NewsService newsService;

    @Override
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto) {
        return newsService.saveNews(dto, WmMediaConstans.WM_NEWS_SUMMIT_STATUS);
    }

    @Override
    @PostMapping("/save_draft")
    public ResponseResult saveDraftNews(@RequestBody WmNewsDto dto) {
        return newsService.saveNews(dto, WmMediaConstans.WM_NEWS_DRAFT_STATUS);
    }

    @Override
    @PostMapping("/list")
    public ResponseResult listByUser(@RequestBody WmNewsPageReqDto dto) {
        return newsService.listByUser(dto);
    }

    @Override
    @PostMapping("/news")
    public ResponseResult wmNews(@RequestBody WmNewsDto dto) {
        return newsService.findWmNewsById(dto);
    }

    @Override
    @PostMapping("/del_news")
    public ResponseResult delNews(@RequestBody WmNewsDto dto) {
        return newsService.delNews(dto);
    }
}
