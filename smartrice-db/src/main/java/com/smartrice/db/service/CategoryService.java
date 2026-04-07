package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceCategory;
import com.smartrice.db.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<SmartRiceCategory> findAll() {
        return categoryMapper.findAll();
    }

    public SmartRiceCategory findById(Integer id) {
        return categoryMapper.findById(id);
    }
}
