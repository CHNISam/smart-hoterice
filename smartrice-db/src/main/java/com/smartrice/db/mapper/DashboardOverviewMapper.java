package com.smartrice.db.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface DashboardOverviewMapper {

    @Select("SELECT COUNT(*) FROM smartrice_admin WHERE deleted = 0")
    int countAdmins();

    @Select("SELECT COUNT(*) FROM smartrice_role WHERE deleted = 0")
    int countRoles();

    @Select("SELECT COUNT(*) FROM smartrice_permission WHERE deleted = 0")
    int countPermissions();

    @Select("SELECT COUNT(*) FROM smartrice_category WHERE deleted = 0")
    int countCategories();

    @Select("SELECT COUNT(*) FROM smartrice_goods WHERE deleted = 0")
    int countGoods();

    @Select("SELECT COUNT(*) FROM smartrice_service_point WHERE deleted = 0")
    int countServicePoints();

    @Select("SELECT COUNT(*) FROM smartrice_delivery_region WHERE deleted = 0")
    int countDeliveryRegions();

    @Select("SELECT COUNT(*) FROM smartrice_driver WHERE deleted = 0")
    int countDrivers();

    @Select("SELECT username, role_ids AS roleIds FROM smartrice_admin WHERE deleted = 0 ORDER BY id ASC LIMIT 5")
    List<Map<String, Object>> findAdminPreview();

    @Select("SELECT name, `level`, keywords FROM smartrice_category WHERE deleted = 0 ORDER BY sort_order ASC, id ASC LIMIT 6")
    List<Map<String, Object>> findCategoryPreview();

    @Select("SELECT name, retail_price AS retailPrice, unit FROM smartrice_goods WHERE deleted = 0 ORDER BY id ASC LIMIT 6")
    List<Map<String, Object>> findGoodsPreview();

    @Select("SELECT name, city, type FROM smartrice_service_point WHERE deleted = 0 ORDER BY id ASC LIMIT 5")
    List<Map<String, Object>> findServicePointPreview();

    @Select("SELECT name, radius_km AS radiusKm, priority FROM smartrice_delivery_region WHERE deleted = 0 ORDER BY priority ASC, id ASC LIMIT 5")
    List<Map<String, Object>> findDeliveryRegionPreview();

    @Select("SELECT username, name, area FROM smartrice_driver WHERE deleted = 0 ORDER BY id ASC LIMIT 5")
    List<Map<String, Object>> findDriverPreview();
}
