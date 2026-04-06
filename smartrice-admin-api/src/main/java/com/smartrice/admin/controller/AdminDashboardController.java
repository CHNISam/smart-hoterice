package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @GetMapping("/info")
    public Object info() {
        Map<String, Object> data = new HashMap<>();
        data.put("todayOrderCount", 0);
        data.put("todayRevenue", 0);
        data.put("pendingOrders", 0);
        data.put("deliveryAbnormal", 0);
        return ResponseUtil.ok(data);
    }
}
