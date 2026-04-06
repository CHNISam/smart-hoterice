package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceAdmin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("SELECT * FROM smartrice_admin WHERE username = #{username} AND deleted = 0")
    SmartRiceAdmin findByUsername(@Param("username") String username);

    @Select("SELECT * FROM smartrice_admin WHERE id = #{id} AND deleted = 0")
    SmartRiceAdmin findById(@Param("id") Integer id);

    @Update("UPDATE smartrice_admin SET last_login_ip = #{lastLoginIp}, last_login_time = #{lastLoginTime} WHERE id = #{id}")
    void updateLoginInfo(@Param("id") Integer id, @Param("lastLoginIp") String ip, @Param("lastLoginTime") java.time.LocalDateTime time);

    @Select("SELECT * FROM smartrice_admin WHERE deleted = 0")
    List<SmartRiceAdmin> findAll();
}
