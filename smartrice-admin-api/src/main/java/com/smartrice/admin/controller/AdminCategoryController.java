package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/category")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Object list() {
        return ResponseUtil.ok(categoryService.findAll());
    }
}
