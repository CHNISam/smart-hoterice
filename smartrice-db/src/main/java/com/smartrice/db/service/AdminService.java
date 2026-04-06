package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRiceAdmin;
import com.smartrice.db.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public SmartRiceAdmin findByUsername(String username) {
        return adminMapper.findByUsername(username);
    }

    public SmartRiceAdmin findById(Integer id) {
        return adminMapper.findById(id);
    }

    public void updateLoginInfo(Integer id, String ip) {
        adminMapper.updateLoginInfo(id, ip, LocalDateTime.now());
    }

    public List<SmartRiceAdmin> findAll() {
        return adminMapper.findAll();
    }
}
