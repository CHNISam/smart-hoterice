package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceServicePointGoods;
import com.smartrice.db.service.ServicePointGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/service-point-goods")
public class AdminServicePointGoodsController {

    @Autowired
    private ServicePointGoodsService servicePointGoodsService;

    @GetMapping("/list")
    public Object list(@RequestParam(required = false) Integer servicePointId) {
        if (servicePointId != null) {
            return ResponseUtil.ok(servicePointGoodsService.findByServicePointIdWithDetails(servicePointId));
        }
        return ResponseUtil.ok(servicePointGoodsService.findAllWithDetails());
    }

    @PostMapping("/save")
    public Object save(@RequestBody SmartRiceServicePointGoods relation) {
        if (relation.getServicePointId() == null || relation.getGoodsId() == null) {
            return ResponseUtil.badArgument();
        }
        if (relation.getEnabled() == null) {
            relation.setEnabled(true);
        }
        return ResponseUtil.ok(servicePointGoodsService.save(relation));
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody SmartRiceServicePointGoods relation) {
        if (relation.getId() == null) {
            return ResponseUtil.badArgument();
        }
        return servicePointGoodsService.deleteById(relation.getId()) ? ResponseUtil.ok() : ResponseUtil.badArgumentValue();
    }
}
