package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRicePermission;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PermissionMapper {

    @Select("SELECT * FROM smartrice_permission WHERE role_id = #{roleId} AND deleted = 0")
    List<SmartRicePermission> findByRoleId(@Param("roleId") Integer roleId);
}
