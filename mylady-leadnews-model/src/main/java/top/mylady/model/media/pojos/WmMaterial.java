package top.mylady.model.media.pojos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


/**
 * 素材上传接口
 */
@Data
public class WmMaterial {
    private Integer id;

    @IdEncrypt
    private Long userId;

    private String url;
    private short type;
    private Short isCollection;
    private Date createdTime;
}