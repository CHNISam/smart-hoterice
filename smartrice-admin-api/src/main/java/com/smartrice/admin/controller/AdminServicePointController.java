package com.smartrice.admin.controller;

import com.smartrice.core.util.ResponseUtil;
import com.smartrice.db.domain.SmartRiceServicePoint;
import com.smartrice.db.service.ServicePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/service-point")
public class AdminServicePointController {

    @Autowired
    private ServicePointService servicePointService;

    @GetMapping("/list")
    public Object list() {
        return ResponseUtil.ok(servicePointService.findAll());
    }

    @PostMapping("/create")
    public Object create(@RequestBody SmartRiceServicePoint point) {
        return ResponseUtil.ok(servicePointService.create(point));
    }

    @PostMapping("/update")
    public Object update(@RequestBody SmartRiceServicePoint point) {
        return servicePointService.update(point) ? ResponseUtil.ok(point) : ResponseUtil.badArgumentValue();
    }

    @PostMapping("/delete")
    public Object delete(@RequestBody SmartRiceServicePoint point) {
        if (point.getId() == null) {
            return ResponseUtil.badArgument();
        }
        return servicePointService.deleteById(point.getId()) ? ResponseUtil.ok() : ResponseUtil.badArgumentValue();
    }
}
