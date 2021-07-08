package top.mylady.behavior.service;

import top.mylady.model.behavior.dtos.ShowBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;


public interface AppShowBehaviorService {
    ResponseResult saveShowBehavior(ShowBehaviorDto dto);
}
