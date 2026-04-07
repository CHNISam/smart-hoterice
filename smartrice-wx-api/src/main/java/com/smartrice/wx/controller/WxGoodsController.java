package com.smartrice.wx.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceGoods;
import com.smartrice.db.service.GoodsService;
import com.smartrice.db.service.ServicePointGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/wx/goods")
public class WxGoodsController {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ServicePointGoodsService servicePointGoodsService;

    @GetMapping("/list")
    public Object list(@RequestParam(required = false) Integer categoryId,
                       @RequestParam(required = false) Integer servicePointId) {
        if (servicePointId != null) {
            List<Map<String, Object>> goods = servicePointGoodsService.findEnabledGoodsByServicePointId(servicePointId);
            if (categoryId != null) {
                goods = goods.stream()
                        .filter(item -> categoryId.equals(item.get("categoryId")) || item.get("categoryId") == null)
                        .collect(Collectors.toList());
            }
            return ResponseUtil.ok(goods);
        }
        List<SmartRiceGoods> goods = goodsService.findAll();
        if (categoryId != null) {
            goods = goods.stream()
                    .filter(item -> categoryId.equals(item.getCategoryId()))
                    .collect(Collectors.toList());
        }
        return ResponseUtil.ok(goods);
    }

    @GetMapping("/detail")
    public Object detail(@RequestParam Integer id) {
        SmartRiceGoods goods = goodsService.findById(id);
        return goods == null ? ResponseUtil.badArgumentValue() : ResponseUtil.ok(goods);
    }
}
