package top.mylady.model.user.pojos;

import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


/**
 * 用户搜索记录
 */
@Data
public class ApUserSearch {
    private Integer id;

    @IdEncrypt
    private Integer entryId;

    private String keyword;
    private Integer status;
    private Date createdTime;

}