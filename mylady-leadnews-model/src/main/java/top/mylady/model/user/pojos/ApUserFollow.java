package top.mylady.model.user.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


/**
 * 用户关注信息
 */
@Data
public class ApUserFollow {

    private Long id;

    @IdEncrypt
    private Long userId;

    @IdEncrypt
    private Integer followId;
    private String followName;
    private Short level;
    private Boolean isNotice;
    private Date createdTime;

    @JsonIgnore
    private String burst;
}