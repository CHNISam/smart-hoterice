package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceServicePointGoods;
import com.smartrice.db.mapper.ServicePointGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ServicePointGoodsService {

    @Autowired
    private ServicePointGoodsMapper servicePointGoodsMapper;

    public List<Map<String, Object>> findAllWithDetails() {
        return servicePointGoodsMapper.findAllWithDetails();
    }

    public List<Map<String, Object>> findEnabledGoodsByServicePointId(Integer servicePointId) {
        return servicePointGoodsMapper.findEnabledGoodsByServicePointId(servicePointId);
    }

    public SmartRiceServicePointGoods save(SmartRiceServicePointGoods relation) {
        SmartRiceServicePointGoods existing = servicePointGoodsMapper.findByServicePointIdAndGoodsId(
                relation.getServicePointId(),
                relation.getGoodsId()
        );
        if (existing != null) {
            existing.setPrice(relation.getPrice());
            existing.setEnabled(relation.getEnabled());
            servicePointGoodsMapper.update(existing);
            return existing;
        }
        servicePointGoodsMapper.insert(relation);
        return relation;
    }

    public boolean deleteById(Integer id) {
        return servicePointGoodsMapper.deleteById(id) > 0;
    }
}
