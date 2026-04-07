package com.smartrice.wx.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx/catalog")
public class WxCatalogController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public Object index() {
        return ResponseUtil.ok(categoryService.findAll());
    }
}
