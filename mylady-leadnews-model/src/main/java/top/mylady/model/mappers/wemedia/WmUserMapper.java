package top.mylady.model.mappers.wemedia;


import top.mylady.model.media.pojos.WmUser;

/**
 * MyBatis接口
 */
public interface WmUserMapper {

    /**
     * 通过名字查询媒体人
     */
    WmUser selectByName(String name);

    WmUser selectById(Long id);
}
