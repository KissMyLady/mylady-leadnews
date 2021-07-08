package top.mylady.model.mappers.app;

import top.mylady.model.behavior.pojos.ApReadBehavior;

/**
 * 阅读行为, 阅读时间, 阅读百分比
 */
public interface ApReadBehaviorMapper {

    int insertReadBehavior(ApReadBehavior record);

    int updataReadBehavior(ApReadBehavior record);

    ApReadBehavior selectReadBehavior(String burst, Integer enrtyId, Integer articleId);

}
