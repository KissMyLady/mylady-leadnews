package top.mylady.media.apis;

import org.springframework.web.multipart.MultipartFile;
import top.mylady.model.common.dtos.ResponseResult;
import top.mylady.model.media.dtos.WmMaterialDto;
import top.mylady.model.media.dtos.WmMaterialListDto;


public interface MaterialManageControllerApi {

    /**
     * 上传图片
     */
    public ResponseResult uploadPicture(MultipartFile file);

    /**
     * 删除图片
     */
    public ResponseResult delPicture(WmMaterialDto dto);

    /**
     * 分页查询
     */
    public ResponseResult list(WmMaterialListDto dto);

    /**
     * 收藏
     */
    public ResponseResult collectionMaterial(WmMaterialDto dto);

    /**
     * 取消收藏
     */
    public ResponseResult cancelCollectionMaterial(WmMaterialDto dto);
}
