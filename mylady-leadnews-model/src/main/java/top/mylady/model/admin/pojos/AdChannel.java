package top.mylady.model.admin.pojos;
import lombok.Data;
import java.util.Date;


/**
 * AdChanner实体类
 */
@Data
public class AdChannel {

    private Integer id;
    private String name;
    private String description;
    private Boolean isDefault;
    private Boolean status;
    private Byte ord;
    private Date createdTime;

}