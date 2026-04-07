package com.smartrice.admin.controller;

import com.smartrice.db.service.GoodsService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminGoodsControllerTest {

    private final AdminGoodsController controller = new AdminGoodsController();

    @Test
    void detailReturnsBadArgumentValueWhenGoodsMissing() {
        injectGoodsService(new GoodsService() {
            @Override
            public com.smartrice.db.domain.SmartRiceGoods findById(Integer id) {
                return null;
            }
        });

        Map<String, Object> result = cast(controller.detail(999));

        assertEquals(402, result.get("errno"));
        assertEquals("参数值不对", result.get("errmsg"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> cast(Object value) {
        return (Map<String, Object>) value;
    }

    private void injectGoodsService(GoodsService goodsService) {
        try {
            Field field = AdminGoodsController.class.getDeclaredField("goodsService");
            field.setAccessible(true);
            field.set(controller, goodsService);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
