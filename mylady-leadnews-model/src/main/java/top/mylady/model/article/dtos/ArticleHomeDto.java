package top.mylady.model.article.dtos;
import lombok.Data;
import java.util.Date;


@Data
public class ArticleHomeDto {

    // 省市
    Integer provinceId;

    // 市区
    Integer cityId;

    // 区县
    Integer countyId;

    // 最大时间 传输传输进入时, 进行最大时间存在校验
    Date maxBehotTime;

    // 最小时间, 传输传输进入时, 进行最小时间存在校验
    Date minBehotTime;

    // 分页size
    Integer size;

    // 数据范围，比如频道ID
    String tag;

}
