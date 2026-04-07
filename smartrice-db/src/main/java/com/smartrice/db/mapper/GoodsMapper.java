package com.smartrice.db.mapper;

import com.smartrice.db.domain.SmartRiceGoods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Select("SELECT * FROM smartrice_goods WHERE deleted = 0 ORDER BY sort_order ASC, id DESC")
    List<SmartRiceGoods> findAll();

    @Select("SELECT * FROM smartrice_goods WHERE id = #{id} AND deleted = 0")
    SmartRiceGoods findById(Integer id);

    @Insert("""
            INSERT INTO smartrice_goods
            (goods_sn, name, category_id, brand_id, gallery, keywords, brief, is_on_sale, sort_order, pic_url, share_url, is_new, is_hot, unit, counter_price, retail_price, detail)
            VALUES
            (#{goodsSn}, #{name}, #{categoryId}, #{brandId}, #{gallery}, #{keywords}, #{brief}, #{isOnSale}, #{sortOrder}, #{picUrl}, #{shareUrl}, #{isNew}, #{isHot}, #{unit}, #{counterPrice}, #{retailPrice}, #{detail})
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(SmartRiceGoods goods);

    @Update("""
            UPDATE smartrice_goods
            SET goods_sn = #{goodsSn},
                name = #{name},
                category_id = #{categoryId},
                brand_id = #{brandId},
                gallery = #{gallery},
                keywords = #{keywords},
                brief = #{brief},
                is_on_sale = #{isOnSale},
                sort_order = #{sortOrder},
                pic_url = #{picUrl},
                share_url = #{shareUrl},
                is_new = #{isNew},
                is_hot = #{isHot},
                unit = #{unit},
                counter_price = #{counterPrice},
                retail_price = #{retailPrice},
                detail = #{detail}
            WHERE id = #{id} AND deleted = 0
            """)
    int update(SmartRiceGoods goods);

    @Update("UPDATE smartrice_goods SET deleted = 1 WHERE id = #{id} AND deleted = 0")
    int deleteById(Integer id);
}
