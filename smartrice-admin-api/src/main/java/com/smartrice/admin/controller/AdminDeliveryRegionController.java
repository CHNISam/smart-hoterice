package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceDeliveryRegion;
import com.smartrice.db.service.DeliveryRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/delivery-region")
public class AdminDeliveryRegionController {

    @Autowired
    private DeliveryRegionService deliveryRegionService;

    @GetMapping("/list")
    public Object list() {
        return ResponseUtil.ok(deliveryRegionService.findAll());
    }

    @PostMapping("/create")
    public Object create(@RequestBody SmartRiceDeliveryRegion region) {
        return ResponseUtil.ok(deliveryRegionService.create(region));
    }

    @PostMapping("/update")
    public Object update(@RequestBody SmartRiceDeliveryRegion region) {
        return deliveryRegionService.update(region) ? ResponseUtil.ok(region) : ResponseUtil.badArgumentValue();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody SmartRiceDeliveryRegion region) {
        if (region.getId() == null) {
            return ResponseUtil.badArgument();
        }
        return deliveryRegionService.deleteById(region.getId()) ? ResponseUtil.ok() : ResponseUtil.badArgumentValue();
    }
}
