package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceDeliveryRegion;
import com.smartrice.db.mapper.DeliveryRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryRegionService {

    @Autowired
    private DeliveryRegionMapper deliveryRegionMapper;

    public List<SmartRiceDeliveryRegion> findAll() {
        return deliveryRegionMapper.findAll();
    }

    public SmartRiceDeliveryRegion findById(Integer id) {
        return deliveryRegionMapper.findById(id);
    }

    public SmartRiceDeliveryRegion create(SmartRiceDeliveryRegion region) {
        deliveryRegionMapper.insert(region);
        return region;
    }

    public boolean update(SmartRiceDeliveryRegion region) {
        return deliveryRegionMapper.update(region) > 0;
    }

    public boolean deleteById(Integer id) {
        return deliveryRegionMapper.deleteById(id) > 0;
    }
}
