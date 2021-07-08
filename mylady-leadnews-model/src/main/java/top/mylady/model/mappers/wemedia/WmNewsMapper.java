package top.mylady.model.mappers.wemedia;


import org.apache.ibatis.annotations.Param;
import top.mylady.model.media.dtos.WmNewsPageReqDto;
import top.mylady.model.media.pojos.WmNews;

import java.util.List;

/**
 * 保存文章操作
 */
public interface WmNewsMapper {

    /**
     * 根据主键修改
     */
    int updateByPrimaryKey(WmNews record);

    /**
     * 添加草稿
     */
    int insertNewsForEdit(WmNews record);


    int updateByPrimaryKeySelective(WmNews record);

    /**
     * 查询根据dto条件
     * @param dto
     * @param uid
     * @return
     */
    List<WmNews> selectBySelective(@Param("dto") WmNewsPageReqDto dto, @Param("uid") Long uid);


    WmNews selectNewsDetailByPrimaryKey(Integer id);


    int deleteByPrimaryKey(Integer id);


    int countSelectBySelective(@Param("dto") WmNewsPageReqDto dto, @Param("uid") Long uid);
}
