package com.smartrice.wx.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.service.ServicePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/service-point")
public class WxServicePointController {

    @Autowired
    private ServicePointService servicePointService;

    @GetMapping("/list")
    public Object list() {
        return ResponseUtil.ok(servicePointService.findAll());
    }
}
