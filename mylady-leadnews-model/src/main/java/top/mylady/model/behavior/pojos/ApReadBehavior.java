package top.mylady.model.behavior.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import top.mylady.model.annotation.IdEncrypt;
import lombok.Data;

import java.util.Date;


/**
 * 阅读行为, 阅读时间, 阅读百分比
 */
@Data
public class ApReadBehavior {
    private Long id;

    @IdEncrypt
    private Integer entryId;

    @IdEncrypt
    private Integer articleId;
    private Short count;
    private Integer readDuration;
    private Short percentage;
    private Short loadDuration;
    private Date createdTime;
    private Date updatedTime;

    @JsonIgnore
    private String burst;
}