package com.smartrice.admin.controller;

import com.smartrice.admin.service.AdminDashboardService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdminDashboardControllerTest {

    private final AdminDashboardController controller = new AdminDashboardController();

    @Test
    void infoReturnsSeedBaselineOverview() {
        injectDashboardService(new AdminDashboardService() {
            @Override
            public Map<String, Object> buildOverview() {
                return Map.of(
                        "version", "V0.1.0",
                        "summary", Map.of("categories", 4, "goods", 6),
                        "sections", List.of(
                                Map.of(
                                        "key", "categories",
                                        "title", "商品分类",
                                        "count", 4,
                                        "rows", List.of(Map.of("name", "丝苗米"))
                                )
                        )
                );
            }
        });

        Map<String, Object> result = cast(controller.info());
        Map<String, Object> data = cast(result.get("data"));
        Map<String, Object> summary = cast(data.get("summary"));
        List<Map<String, Object>> sections = castList(data.get("sections"));

        assertEquals(0, result.get("errno"));
        assertEquals("V0.1.0", data.get("version"));
        assertEquals(4, summary.get("categories"));
        assertEquals("商品分类", sections.get(0).get("title"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> cast(Object value) {
        return (Map<String, Object>) value;
    }

    @SuppressWarnings("unchecked")
    private List<Map<String, Object>> castList(Object value) {
        return (List<Map<String, Object>>) value;
    }

    private void injectDashboardService(AdminDashboardService service) {
        try {
            Field field = AdminDashboardController.class.getDeclaredField("dashboardService");
            field.setAccessible(true);
            field.set(controller, service);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
