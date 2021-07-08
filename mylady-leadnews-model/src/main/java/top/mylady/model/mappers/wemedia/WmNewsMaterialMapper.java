package top.mylady.model.mappers.wemedia;


import java.util.Map;

public interface WmNewsMaterialMapper {

    /**
     * 删除时, 如果对应的material有关联引用, 则不能删除, 所以需要查询对应的引用数据
     */
    int countByMid(Integer mid);

    /**
     * 根据id删除文章
     */
    int deleteByNewsId(Integer nid);

    /**
     * 保存文章和图片的关系
     */
    void saveRelationByContent(Map<String, Object> materials, Integer newsId, Short type);
}
