package com.smartrice.admin.config;

import com.smartrice.core.util.JwtUtil;
import com.smartrice.core.util.ResponseUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminAuthInterceptor(jwtUtil))
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/auth/login");
    }

    static class AdminAuthInterceptor implements HandlerInterceptor {

        private final JwtUtil jwtUtil;
        private final ObjectMapper objectMapper = new ObjectMapper();

        AdminAuthInterceptor(JwtUtil jwtUtil) {
            this.jwtUtil = jwtUtil;
        }

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return true;
            }

            String token = request.getHeader("X-SmartRice-Admin-Token");
            if (token == null || token.isEmpty()) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.unlogin()));
                return false;
            }

            Integer adminId = jwtUtil.parseToken(token);
            if (adminId == null) {
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(ResponseUtil.unlogin()));
                return false;
            }

            request.setAttribute("adminId", adminId);
            return true;
        }
    }
}
