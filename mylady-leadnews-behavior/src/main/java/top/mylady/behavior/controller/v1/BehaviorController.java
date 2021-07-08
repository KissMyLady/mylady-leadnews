package top.mylady.behavior.controller.v1;

import org.springframework.web.bind.annotation.RequestBody;
import top.mylady.article.apis.BehaviorControllerApi;
import top.mylady.behavior.service.AppLikeBehaviorService;
import top.mylady.behavior.service.AppReadBehaviorService;
import top.mylady.behavior.service.AppShowBehaviorService;
import top.mylady.model.behavior.dtos.LikesBehaviorDto;
import top.mylady.model.behavior.dtos.ReadBehaviorDto;
import top.mylady.model.behavior.dtos.ShowBehaviorDto;
import top.mylady.model.behavior.dtos.UnLikesBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/behavior")
public class BehaviorController implements BehaviorControllerApi {

    @Autowired
    private AppShowBehaviorService appShowBehaviorService;

    @Autowired
    private AppLikeBehaviorService appLikeBehaviorService;

    /**
     * 关注
     */
    @Override
    @RequestMapping("/save_behavior")
    public ResponseResult saveShowBehavior(ShowBehaviorDto dto) {
        return appShowBehaviorService.saveShowBehavior(dto);
    }

    /**
     * 点赞
     */
    @Override
    @RequestMapping("/like_behavior")
    public ResponseResult saveLikeBehavior(@RequestBody LikesBehaviorDto likesBehaviorDto) {
        return appLikeBehaviorService.saveLikeBehavior(likesBehaviorDto);
    }


    /**
     * 不喜欢| 取消不喜欢
     */
    @Override
    @RequestMapping("/unlike_behavior")
    public ResponseResult saveUnLikeBehaviorApiInterface(@RequestBody UnLikesBehaviorDto dto) {
        return appLikeBehaviorService.saveUnLikeBehavior(dto);
    }

    @Autowired
    private AppReadBehaviorService appReadBehaviorService;

    /**
     * 阅读行为
     */
    @Override
    public ResponseResult saveReadBehavior(ReadBehaviorDto readBehaviorDto) {
        return appReadBehaviorService.saveReadBehavior(readBehaviorDto);
    }
}
