package com.smartrice.admin.config;

import com.smartrice.core.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminWebMvcConfigTest {

    private final JwtUtil jwtUtil = new JwtUtil("smart-rice-test-secret-key-smart-rice", 3600);
    private final AdminWebMvcConfig.AdminAuthInterceptor interceptor =
            new AdminWebMvcConfig.AdminAuthInterceptor(jwtUtil);

    @Test
    void preHandleRejectsMissingToken() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/admin/dashboard/info");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = interceptor.preHandle(request, response, new Object());

        assertFalse(allowed);
        assertEquals(HttpServletResponse.SC_OK, response.getStatus());
        assertEquals("{\"errno\":501,\"errmsg\":\"请登录\"}", response.getContentAsString());
    }

    @Test
    void preHandleAllowsOptionsRequests() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("OPTIONS", "/admin/dashboard/info");
        MockHttpServletResponse response = new MockHttpServletResponse();

        boolean allowed = interceptor.preHandle(request, response, new Object());

        assertTrue(allowed);
    }
}
