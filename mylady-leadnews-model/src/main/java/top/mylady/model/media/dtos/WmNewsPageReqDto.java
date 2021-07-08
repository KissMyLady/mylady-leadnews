package top.mylady.model.media.dtos;

import top.mylady.model.annotation.IdEncrypt;
import top.mylady.model.common.dtos.PageRequestDto;
import lombok.Data;

import java.util.Date;


@Data
public class WmNewsPageReqDto extends PageRequestDto {

    private Short status;
    private Date beginPubdate;
    private Date endPubdate;
    @IdEncrypt
    private Integer channelId;
    private String keyWord;
}
