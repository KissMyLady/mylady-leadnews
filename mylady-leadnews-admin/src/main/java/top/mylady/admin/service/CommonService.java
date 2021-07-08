package top.mylady.admin.service;


import top.mylady.model.admin.dtos.CommonDto;
import top.mylady.model.common.dtos.ResponseResult;

/**
 * 通用接口
 */
public interface CommonService {

    /**
     * 列表查询-->无条件查询，无条件统计    有条件的查询 有条件的统计
     */
    public ResponseResult list(CommonDto dto);

    /**
     * 通过dto中的model来判断，选择使用新增或修改
     */
    public ResponseResult update(CommonDto dto);

    /**
     * 通用的删除
     */
    public ResponseResult delete(CommonDto dto);
}
