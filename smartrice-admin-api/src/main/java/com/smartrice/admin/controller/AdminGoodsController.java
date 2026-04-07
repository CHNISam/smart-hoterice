package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceGoods;
import com.smartrice.db.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/goods")
public class AdminGoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public Object list() {
        return ResponseUtil.ok(goodsService.findAll());
    }

    @GetMapping("/detail")
    public Object detail(@RequestParam Integer id) {
        SmartRiceGoods goods = goodsService.findById(id);
        return goods == null ? ResponseUtil.badArgumentValue() : ResponseUtil.ok(goods);
    }

    @PostMapping("/create")
    public Object create(@RequestBody SmartRiceGoods goods) {
        return ResponseUtil.ok(goodsService.create(goods));
    }

    @PostMapping("/update")
    public Object update(@RequestBody SmartRiceGoods goods) {
        return goodsService.update(goods) ? ResponseUtil.ok(goods) : ResponseUtil.badArgumentValue();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody SmartRiceGoods goods) {
        if (goods.getId() == null) {
            return ResponseUtil.badArgument();
        }
        return goodsService.deleteById(goods.getId()) ? ResponseUtil.ok() : ResponseUtil.badArgumentValue();
    }
}
