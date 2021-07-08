package top.mylady.model.behavior.dtos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


@Data
public class FollowBehaviorDto {
    // 设备ID
    @IdEncrypt
    Integer equipmentId;

    // 文章ID
    @IdEncrypt
    Integer articleId;

    @IdEncrypt
    Integer followId;
}
