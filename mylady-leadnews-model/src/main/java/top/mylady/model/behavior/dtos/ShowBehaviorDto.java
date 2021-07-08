package top.mylady.model.behavior.dtos;


import top.mylady.model.annotation.IdEncrypt;
import top.mylady.model.article.pojos.ApArticle;
import lombok.Data;
import java.util.List;


@Data
public class ShowBehaviorDto {

    // 设备ID
    @IdEncrypt
    Integer equipmentId;

    List<ApArticle> articleIds;

}
