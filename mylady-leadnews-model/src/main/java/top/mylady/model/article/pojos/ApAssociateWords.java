package top.mylady.model.article.pojos;

import lombok.Data;

import java.util.Date;


/**
 * 联想词
 */
@Data
public class ApAssociateWords {

    private Integer id;
    private String associateWords;
    private Date createdTime;

}