package top.mylady.model.mappers.admin;
import top.mylady.model.admin.pojos.AdChannelLabel;


public interface AdChannelLabelMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(AdChannelLabel record);

    int insertSelective(AdChannelLabel record);

    AdChannelLabel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdChannelLabel record);

    int updateByPrimaryKey(AdChannelLabel record);

    /**
     * 根据labelId查询
     * @param id
     * @return
     */
    AdChannelLabel selectByLabelId(Integer id);
}
