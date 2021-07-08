package top.mylady.model.mappers.wemedia;

import top.mylady.model.media.dtos.WmMaterialListDto;
import top.mylady.model.media.pojos.WmMaterial;

import java.util.Collection;
import java.util.List;


/**
 * 素材上传接口
 */
public interface WmMaterialMapper {
    int insertMaterial(WmMaterial wmMaterial);

    /**
     * id查询媒体文件
     */
    WmMaterial selectByMaterialId(Integer id);

    /**
     * 根据id删除媒体文件
     */
    int deleteByMaterialId(Integer id);

    /**
     * 根据用户id查询需要查询的图片状态(是否收藏), 查询图片
     */
    List<WmMaterial> findListByUidAndStatus(WmMaterialListDto dto, Long uid);

    /**
     * 进行分页统计
     */
    int countListByUidAndStatus(WmMaterialListDto dto, Long uid);

    /**
     * 收藏, 取消收藏接口
     */
    int updateStatusByUidAndId(Integer id, Long userId, Short type);

    /**
     * 保存草稿, 发布
     */
    List<WmMaterial> findMaterialByUidAndImgUrls(Long id, Collection<Object> values);
}
