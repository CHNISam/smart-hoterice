package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceDeliveryRegion;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DeliveryRegionMapper {

    @Select("SELECT * FROM smartrice_delivery_region WHERE deleted = 0 ORDER BY priority ASC, id ASC")
    List<SmartRiceDeliveryRegion> findAll();

    @Select("SELECT * FROM smartrice_delivery_region WHERE id = #{id} AND deleted = 0")
    SmartRiceDeliveryRegion findById(Integer id);

    @Insert("""
            INSERT INTO smartrice_delivery_region
            (name, center_lat, center_lng, radius_km, priority, min_batch_threshold, driver_id, service_point_id, enabled)
            VALUES
            (#{name}, #{centerLat}, #{centerLng}, #{radiusKm}, #{priority}, #{minBatchThreshold}, #{driverId}, #{servicePointId}, #{enabled})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmartRiceDeliveryRegion region);

    @Update("""
            UPDATE smartrice_delivery_region
            SET name = #{name},
                center_lat = #{centerLat},
                center_lng = #{centerLng},
                radius_km = #{radiusKm},
                priority = #{priority},
                min_batch_threshold = #{minBatchThreshold},
                driver_id = #{driverId},
                service_point_id = #{servicePointId},
                enabled = #{enabled}
            WHERE id = #{id} AND deleted = 0
            """)
    int update(SmartRiceDeliveryRegion region);

    @Update("UPDATE smartrice_delivery_region SET deleted = 1 WHERE id = #{id} AND deleted = 0")
    int deleteById(Integer id);
}
