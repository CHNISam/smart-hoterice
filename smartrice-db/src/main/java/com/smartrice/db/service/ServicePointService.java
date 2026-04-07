package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceServicePoint;
import com.smartrice.db.mapper.ServicePointMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicePointService {

    @Autowired
    private ServicePointMapper servicePointMapper;

    public List<SmartRiceServicePoint> findAll() {
        return servicePointMapper.findAll();
    }

    public SmartRiceServicePoint findById(Integer id) {
        return servicePointMapper.findById(id);
    }

    public SmartRiceServicePoint create(SmartRiceServicePoint point) {
        servicePointMapper.insert(point);
        return point;
    }

    public boolean update(SmartRiceServicePoint point) {
        return servicePointMapper.update(point) > 0;
    }

    public boolean deleteById(Integer id) {
        return servicePointMapper.deleteById(id) > 0;
    }
}
