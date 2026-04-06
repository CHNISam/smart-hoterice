package com.smartrice.admin.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartrice.core.util.JwtUtil;
import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceAdmin;
import com.smartrice.db.service.AdminService;
import com.smartrice.db.service.RoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/login")
    public Object login(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String username = body.get("username");
        String password = body.get("password");

        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        SmartRiceAdmin admin = adminService.findByUsername(username);
        if (admin == null || !encoder.matches(password, admin.getPassword())) {
            return ResponseUtil.fail(605, "用户名或密码不正确");
        }

        String ip = request.getRemoteAddr();
        adminService.updateLoginInfo(admin.getId(), ip);

        String token = jwtUtil.generateToken(admin.getId());

        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("adminInfo", buildAdminInfo(admin));
        return ResponseUtil.ok(data);
    }

    @PostMapping("/logout")
    public Object logout() {
        return ResponseUtil.ok();
    }

    @GetMapping("/info")
    public Object info(@RequestHeader(value = "X-SmartRice-Admin-Token", required = false) String token) {
        if (token == null || token.isEmpty()) {
            return ResponseUtil.unlogin();
        }

        Integer adminId = jwtUtil.parseToken(token);
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        SmartRiceAdmin admin = adminService.findById(adminId);
        if (admin == null) {
            return ResponseUtil.unlogin();
        }

        List<Integer> roleIds = parseRoleIds(admin.getRoleIds());
        Set<String> permissions = roleService.getPermissionsByRoleIds(roleIds);

        Map<String, Object> data = new HashMap<>();
        data.put("name", admin.getUsername());
        data.put("avatar", admin.getAvatar() != null ? admin.getAvatar() : "");
        data.put("roles", roleIds);
        data.put("perms", permissions);
        return ResponseUtil.ok(data);
    }

    private Map<String, Object> buildAdminInfo(SmartRiceAdmin admin) {
        Map<String, Object> info = new HashMap<>();
        info.put("nickName", admin.getUsername());
        info.put("avatar", admin.getAvatar() != null ? admin.getAvatar() : "");
        return info;
    }

    private List<Integer> parseRoleIds(String roleIdsJson) {
        try {
            if (roleIdsJson == null || roleIdsJson.isEmpty()) return Collections.emptyList();
            return objectMapper.readValue(roleIdsJson, new TypeReference<List<Integer>>() {});
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
