package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceServicePoint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ServicePointMapper {

    @Select("SELECT * FROM smartrice_service_point WHERE deleted = 0 ORDER BY id DESC")
    List<SmartRiceServicePoint> findAll();

    @Select("SELECT * FROM smartrice_service_point WHERE id = #{id} AND deleted = 0")
    SmartRiceServicePoint findById(Integer id);

    @Insert("""
            INSERT INTO smartrice_service_point
            (name, type, city, address, latitude, longitude, phone, distance, image_url, business_hours, description, facilities, enabled)
            VALUES
            (#{name}, #{type}, #{city}, #{address}, #{latitude}, #{longitude}, #{phone}, #{distance}, #{imageUrl}, #{businessHours}, #{description}, #{facilities}, #{enabled})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmartRiceServicePoint point);

    @Update("""
            UPDATE smartrice_service_point
            SET name = #{name},
                type = #{type},
                city = #{city},
                address = #{address},
                latitude = #{latitude},
                longitude = #{longitude},
                phone = #{phone},
                distance = #{distance},
                image_url = #{imageUrl},
                business_hours = #{businessHours},
                description = #{description},
                facilities = #{facilities},
                enabled = #{enabled}
            WHERE id = #{id} AND deleted = 0
            """)
    int update(SmartRiceServicePoint point);

    @Update("UPDATE smartrice_service_point SET deleted = 1 WHERE id = #{id} AND deleted = 0")
    int deleteById(Integer id);
}
