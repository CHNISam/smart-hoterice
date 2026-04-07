package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceRole;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM smartrice_role WHERE id = #{id} AND deleted = 0")
    SmartRiceRole findById(@Param("id") Integer id);

    @Select("SELECT * FROM smartrice_role WHERE deleted = 0")
    List<SmartRiceRole> findAll();
}
