package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceServicePointGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface ServicePointGoodsMapper {

    @Select("""
            SELECT spg.id,
                   spg.service_point_id AS servicePointId,
                   spg.goods_id AS goodsId,
                   spg.price,
                   spg.enabled,
                   sp.name AS servicePointName,
                   g.name AS goodsName,
                   g.retail_price AS retailPrice,
                   g.unit
            FROM smartrice_service_point_goods spg
            LEFT JOIN smartrice_service_point sp ON spg.service_point_id = sp.id
            LEFT JOIN smartrice_goods g ON spg.goods_id = g.id
            WHERE spg.deleted = 0
            ORDER BY spg.id DESC
            """)
    List<Map<String, Object>> findAllWithDetails();

    @Select("""
            SELECT spg.id,
                   spg.service_point_id AS servicePointId,
                   spg.goods_id AS goodsId,
                   spg.price,
                   spg.enabled,
                   sp.name AS servicePointName,
                   g.name AS goodsName,
                   g.category_id AS categoryId,
                   g.retail_price AS retailPrice,
                   g.unit
            FROM smartrice_service_point_goods spg
            LEFT JOIN smartrice_service_point sp ON spg.service_point_id = sp.id
            LEFT JOIN smartrice_goods g ON spg.goods_id = g.id
            WHERE spg.deleted = 0
              AND spg.service_point_id = #{servicePointId}
            ORDER BY spg.id DESC
            """)
    List<Map<String, Object>> findByServicePointIdWithDetails(Integer servicePointId);

    @Select("""
            SELECT spg.id,
                   spg.service_point_id AS servicePointId,
                   spg.goods_id AS goodsId,
                   spg.price,
                   spg.enabled,
                   g.goods_sn AS goodsSn,
                   g.category_id AS categoryId,
                   g.name,
                   g.brief,
                   g.pic_url AS picUrl,
                   g.retail_price AS retailPrice,
                   g.unit
            FROM smartrice_service_point_goods spg
            INNER JOIN smartrice_goods g ON spg.goods_id = g.id
            WHERE spg.deleted = 0
              AND spg.enabled = 1
              AND g.deleted = 0
              AND g.is_on_sale = 1
              AND spg.service_point_id = #{servicePointId}
            ORDER BY spg.id DESC
            """)
    List<Map<String, Object>> findEnabledGoodsByServicePointId(Integer servicePointId);

    @Select("""
            SELECT * FROM smartrice_service_point_goods
            WHERE service_point_id = #{servicePointId}
              AND goods_id = #{goodsId}
              AND deleted = 0
            LIMIT 1
            """)
    SmartRiceServicePointGoods findByServicePointIdAndGoodsId(Integer servicePointId, Integer goodsId);

    @Insert("""
            INSERT INTO smartrice_service_point_goods
            (service_point_id, goods_id, price, enabled)
            VALUES
            (#{servicePointId}, #{goodsId}, #{price}, #{enabled})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmartRiceServicePointGoods relation);

    @Update("""
            UPDATE smartrice_service_point_goods
            SET price = #{price},
                enabled = #{enabled}
            WHERE id = #{id} AND deleted = 0
            """)
    int update(SmartRiceServicePointGoods relation);

    @Update("UPDATE smartrice_service_point_goods SET deleted = 1 WHERE id = #{id} AND deleted = 0")
    int deleteById(Integer id);
}
