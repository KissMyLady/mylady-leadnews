package top.mylady.model.media.dtos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


/**
 * 接收前端传递过来的参数
 */
@Data
public class WmMaterialDto {

    @IdEncrypt
    private Integer id;

//    private String url;
}
