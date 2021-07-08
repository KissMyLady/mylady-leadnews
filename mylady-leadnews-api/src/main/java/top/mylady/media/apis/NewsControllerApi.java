package top.mylady.media.apis;

import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.dtos.WmNewsDto;
import top.mylady.model.media.dtos.WmNewsPageReqDto;


public interface NewsControllerApi {

    /**
     * 保存文章
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 保存草稿
     */
    public ResponseResult saveDraftNews(WmNewsDto dto);

    /**
     * 用户查询文章列表
     */
    public ResponseResult listByUser(WmNewsPageReqDto dto);

    /**
     * 根据id获取文章信息
     */
    public ResponseResult wmNews(WmNewsDto dto);

    /**
     * 删除文章
     */
    public ResponseResult delNews(WmNewsDto dto);
}
