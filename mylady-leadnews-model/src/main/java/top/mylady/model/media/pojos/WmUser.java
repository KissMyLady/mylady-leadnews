package top.mylady.model.media.pojos;

import lombok.Data;

import java.util.Date;


/**
 * 登录后台
 */
@Data
public class WmUser {

    private Long id;
    private String name;
    private String password;
    private String salt;
    private String nickname;
    private String image;
    private String location;
    private String phone;
    private Integer status;
    private String email;
    private Integer type;
    private Integer score;
    private Long apUserId;
    private Integer apAuthorId;
    private Date loginTime;
    private Date createdTime;

}