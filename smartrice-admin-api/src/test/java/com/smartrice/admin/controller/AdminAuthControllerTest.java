package com.smartrice.admin.controller;

import com.smartrice.core.util.JwtUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AdminAuthControllerTest {

    private final AdminAuthController controller = new AdminAuthController();

    @Test
    void loginRejectsMissingArguments() {
        Map<String, String> body = new HashMap<>();
        body.put("username", "admin123");

        Map<String, Object> result = cast(controller.login(body, null));

        assertEquals(401, result.get("errno"));
        assertEquals("参数不对", result.get("errmsg"));
        assertNull(result.get("data"));
    }

    @Test
    void infoReturnsUnloginWhenTokenIsInvalid() {
        injectJwtUtil(new JwtUtil("smart-rice-test-secret-key-smart-rice", 3600));

        Map<String, Object> result = cast(controller.info("invalid-token"));

        assertEquals(501, result.get("errno"));
        assertEquals("请登录", result.get("errmsg"));
        assertNull(result.get("data"));
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> cast(Object value) {
        return (Map<String, Object>) value;
    }

    private void injectJwtUtil(JwtUtil jwtUtil) {
        try {
            Field field = AdminAuthController.class.getDeclaredField("jwtUtil");
            field.setAccessible(true);
            field.set(controller, jwtUtil);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
