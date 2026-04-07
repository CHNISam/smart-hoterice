package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM smartrice_category WHERE deleted = 0 ORDER BY sort_order ASC, id ASC")
    List<SmartRiceCategory> findAll();

    @Select("SELECT * FROM smartrice_category WHERE id = #{id} AND deleted = 0")
    SmartRiceCategory findById(Integer id);
}
