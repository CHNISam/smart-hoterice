package com.smartrice.wx.controller;

import com.smartrice.db.domain.SmartRiceGoods;
import com.smartrice.db.service.GoodsService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WxGoodsControllerTest {

    private final WxGoodsController controller = new WxGoodsController();

    @Test
    void listFiltersByCategory() {
        injectGoodsService(new GoodsService() {
            @Override
            public List<SmartRiceGoods> findAll() {
                return List.of(goods(1, 2, "丝苗米"), goods(2, 3, "油粘米"));
            }
        });

        Map<String, Object> result = cast(controller.list(2, null));
        List<SmartRiceGoods> data = castList(result.get("data"));

        assertEquals(0, result.get("errno"));
        assertEquals(1, data.size());
        assertEquals("丝苗米", data.get(0).getName());
    }

    private SmartRiceGoods goods(int id, int categoryId, String name) {
        SmartRiceGoods goods = new SmartRiceGoods();
        goods.setId(id);
        goods.setCategoryId(categoryId);
        goods.setName(name);
        goods.setRetailPrice(BigDecimal.TEN);
        return goods;
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> cast(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<SmartRiceGoods> castList(Object value) {
        return (List<SmartRiceGoods>) value;
    }

    private void injectGoodsService(GoodsService goodsService) {
        try {
            Field field = WxGoodsController.class.getDeclaredField("goodsService");
            field.setAccessible(true);
            field.set(controller, goodsService);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
