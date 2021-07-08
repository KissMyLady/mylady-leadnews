package top.mylady.model.mappers.app;

import top.mylady.model.article.pojos.ApCollection;


/**
 * 定义按照行为实体ID, 收藏内容ID, 和类型查询收藏方法
 */
public interface ApCollectionMapper {

    /**
     * 选择一个终端收藏数据
     */
    ApCollection selectForEntryId(String burst, Integer objectId, Integer entryId, Short type);
}
