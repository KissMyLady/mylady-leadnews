package top.mylady.admin.service.impl.commfilter;
import top.mylady.model.admin.dtos.CommonDto;
import top.mylady.model.admin.dtos.CommonWhereDto;
import top.mylady.model.admin.pojos.AdUser;


/**
 * 用于定义后置处理接口约束和公用默认方法
 */
public interface BaseCommonFilter {

    void doListAfter(AdUser user, CommonDto dto);
    void doUpdateAfter(AdUser user, CommonDto dto);
    void doInsertAfter(AdUser user, CommonDto dto);
    void doDeleteAfter(AdUser user, CommonDto dto);

    /**
     * 获取更新字段里面的值
     */
    default CommonWhereDto findUpdateValue(String filed, CommonDto dto){
        if(dto!=null){
            for (CommonWhereDto cw : dto.getSets()) {
                if(filed.equals(cw.getFiled())){
                    return cw;
                }
            }
        }
        return null;
    }

    /**
     * 获取查询字段里面的值
     */
    default CommonWhereDto findWhereValue(String filed,CommonDto dto){
        if(dto!=null){
            for (CommonWhereDto cw : dto.getWhere()) {
                if(filed.equals(cw.getFiled())){
                    return cw;
                }
            }
        }
        return null;
    }

}
