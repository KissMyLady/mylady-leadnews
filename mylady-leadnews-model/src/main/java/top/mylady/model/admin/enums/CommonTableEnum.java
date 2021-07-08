package top.mylady.model.admin.enums;

import lombok.Getter;


/**
 * 基本权限的控制定义为枚举类
 */
@Getter
public enum CommonTableEnum {

    /*
    * 频道, 敏感词管理: 进行CRUD操作
    * */
    AD_CHANNEL("*",true,true,true,true),
    AD_SENSITIVE("*",true,true,true,true),

    // APP用户端
    AP_ARTICLE("*",true,false,false,false),
    AP_ARTICLE_CONFIG("*",true,false,true,false),
    AP_USER("*",true,false,true,false);

    String filed;
    boolean list;   //开启列表权限？
    boolean add;    //开启增加权限？
    boolean update; //开启修改权限？
    boolean delete; //开启删除权限？

    CommonTableEnum(String filed,boolean list,boolean add,boolean update,boolean delete){
        this.filed = filed;
        this.list = list;
        this.add = add;
        this.update = update;
        this.delete = delete;
    }
}
