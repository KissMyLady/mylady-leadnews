package top.mylady.model.admin.dtos;

import lombok.Data;


/**
 * 条件封装
 */
@Data
public class CommonWhereDto {

    private String filed;
    private String type="eq";
    private String value;

}
