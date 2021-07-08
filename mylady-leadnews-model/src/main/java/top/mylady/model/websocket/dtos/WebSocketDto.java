package top.mylady.model.websocket.dtos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


@Data
public class WebSocketDto {
    // 设备ID
    @IdEncrypt
    Integer equipmentId;

    // 文章ID
    @IdEncrypt
    String token;
}
