package top.mylady.model.behavior.dtos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


@Data
public class ForwardBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;

    // 文章ID
    @IdEncrypt
    Integer articleId;

    @IdEncrypt
    Integer dynamicId;
}
