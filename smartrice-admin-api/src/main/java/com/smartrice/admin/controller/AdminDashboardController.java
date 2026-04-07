package com.smartrice.admin.controller;

import com.smartrice.admin.service.AdminDashboardService;
import com.smartrice.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    @Autowired
    private AdminDashboardService dashboardService;

    @GetMapping("/info")
    public Object info() {
        return ResponseUtil.ok(dashboardService.buildOverview());
    }
}
