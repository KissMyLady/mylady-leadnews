package top.mylady.model.user.dtos;

import top.mylady.model.common.dtos.PageRequestDto;
import lombok.Data;


@Data
public class UserFansPageReqDto extends PageRequestDto {
    private String fansName;
    private Short level;
}
