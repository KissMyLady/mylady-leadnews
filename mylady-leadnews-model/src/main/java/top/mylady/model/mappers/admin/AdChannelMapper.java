package top.mylady.model.mappers.admin;
import top.mylady.model.admin.pojos.AdChannel;

import java.util.List;


/**
 * 根据id查询频道
 */
public interface AdChannelMapper {

    /**
     * 查询所有
     */
    public List<AdChannel> selectAll();


    AdChannel selectByPrimaryKey(Integer id);
}
