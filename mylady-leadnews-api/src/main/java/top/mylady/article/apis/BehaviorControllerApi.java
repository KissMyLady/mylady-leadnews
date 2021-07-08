package top.mylady.article.apis;
import top.mylady.model.behavior.dtos.LikesBehaviorDto;
import top.mylady.model.behavior.dtos.ReadBehaviorDto;
import top.mylady.model.behavior.dtos.UnLikesBehaviorDto;
import top.mylady.model.behavior.pojos.ApLikesBehavior;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.behavior.dtos.ShowBehaviorDto;


public interface BehaviorControllerApi {

    /**
     * 保存用户点击文章的行为
     * @param dto 封装的数据
     * @return 返回响应结果
     */
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);

    /**
     * 点赞
     */
    ResponseResult saveLikeBehavior(LikesBehaviorDto likesBehaviorDto);

    /**
     * 人家就是不喜欢, 你有什么办法
     */
    ResponseResult saveUnLikeBehaviorApiInterface(UnLikesBehaviorDto dto);

    /**
     * 阅读行为
     */
    ResponseResult saveReadBehavior(ReadBehaviorDto readBehaviorDto);
}
