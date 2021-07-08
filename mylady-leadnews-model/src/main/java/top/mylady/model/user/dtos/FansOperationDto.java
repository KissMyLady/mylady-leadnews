package top.mylady.model.user.dtos;
import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;


@Data
public class FansOperationDto {
    @IdEncrypt
    private Integer fansId;

    /**
     * true 开   false 关
     */
    private Boolean switchState;
}
