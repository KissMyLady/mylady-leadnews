package top.mylady.model.behavior.pojos;

import lombok.Data;

import java.util.Date;


/**
 * App用户关注行为
 */
@Data
public class ApFollowBehavior {

    private Long id;
    private Integer entryId;
    private Integer articleId;
    private Integer followId;
    private Date createdTime;

}