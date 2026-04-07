package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceGoods;
import com.smartrice.db.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    public List<SmartRiceGoods> findAll() {
        return goodsMapper.findAll();
    }

    public SmartRiceGoods findById(Integer id) {
        return goodsMapper.findById(id);
    }

    public SmartRiceGoods create(SmartRiceGoods goods) {
        goodsMapper.insert(goods);
        return goods;
    }

    public boolean update(SmartRiceGoods goods) {
        return goodsMapper.update(goods) > 0;
    }

    public boolean deleteById(Integer id) {
        return goodsMapper.deleteById(id) > 0;
    }
}
