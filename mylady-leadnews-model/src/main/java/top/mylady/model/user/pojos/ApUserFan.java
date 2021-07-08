package top.mylady.model.user.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


/**
 * 用户粉丝信息
 */
@Data
public class ApUserFan {

    private Long id;

    @IdEncrypt
    private Integer userId;

    @IdEncrypt
    private Long fansId;

    private String fansName;
    private Short level;
    private Date createdTime;
    private Boolean isDisplay;
    private Boolean isShieldLetter;
    private Boolean isShieldComment;

    @JsonIgnore
    private String burst;
}