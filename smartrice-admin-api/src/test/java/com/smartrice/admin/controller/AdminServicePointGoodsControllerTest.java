package com.smartrice.admin.controller;

import com.smartrice.db.domain.SmartRiceServicePointGoods;
import com.smartrice.db.service.ServicePointGoodsService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminServicePointGoodsControllerTest {

    private final AdminServicePointGoodsController controller = new AdminServicePointGoodsController();

    @Test
    void saveRejectsMissingBindingKeys() {
        injectService(new ServicePointGoodsService());

        Map<String, Object> result = cast(controller.save(new SmartRiceServicePointGoods()));

        assertEquals(401, result.get("errno"));
        assertEquals("参数不对", result.get("errmsg"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> cast(Object value) {
        return (Map<String, Object>) value;
    }

    private void injectService(ServicePointGoodsService service) {
        try {
            Field field = AdminServicePointGoodsController.class.getDeclaredField("servicePointGoodsService");
            field.setAccessible(true);
            field.set(controller, service);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
