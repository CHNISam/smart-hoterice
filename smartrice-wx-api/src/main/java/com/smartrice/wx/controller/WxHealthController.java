package com.smartrice.wx.controller;

import com.smartrice.core.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class WxHealthController {

    @GetMapping("/health")
    public Object health() {
        return ResponseUtil.ok("wx-api is running");
    }
}
