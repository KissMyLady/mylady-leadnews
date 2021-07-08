package top.mylady.behavior.service;

import top.mylady.model.behavior.dtos.ReadBehaviorDto;
import top.mylady.model.common.dtos.ResponseResult;


/**
 * 阅读行为
 */
public interface AppReadBehaviorService {

    /**
     * 阅读行为
     */
    public ResponseResult saveReadBehavior(ReadBehaviorDto dto);
}
