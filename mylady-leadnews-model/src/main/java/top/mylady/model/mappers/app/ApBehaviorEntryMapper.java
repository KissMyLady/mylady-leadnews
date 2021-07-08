package top.mylady.model.mappers.app;

import top.mylady.model.behavior.pojos.ApBehaviorEntry;
import org.apache.ibatis.annotations.Param;


public interface ApBehaviorEntryMapper {
    ApBehaviorEntry selectByUserIdOrEquipment(
            @Param("userId") Long userId,
            @Param("equipmentId") Integer equipmentId
    );
}
