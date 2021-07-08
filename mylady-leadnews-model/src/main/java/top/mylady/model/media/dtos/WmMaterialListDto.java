package top.mylady.model.media.dtos;

import top.mylady.model.common.dtos.PageRequestDto;
import lombok.Data;


/**
 * 基层了分页校验的封装
 */
@Data
public class WmMaterialListDto extends PageRequestDto {
    Short isCollected; //1 查询收藏的
}
