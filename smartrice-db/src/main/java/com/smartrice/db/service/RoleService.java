package com.smartrice.db.service;

import com.smartrice.db.domain.SmartRicePermission;
import com.smartrice.db.domain.SmartRiceRole;
import com.smartrice.db.mapper.PermissionMapper;
import com.smartrice.db.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public SmartRiceRole findById(Integer id) {
        return roleMapper.findById(id);
    }

    public Set<String> getPermissionsByRoleIds(List<Integer> roleIds) {
        Set<String> permissions = new HashSet<>();
        for (Integer roleId : roleIds) {
            List<SmartRicePermission> perms = permissionMapper.findByRoleId(roleId);
            permissions.addAll(perms.stream().map(SmartRicePermission::getPermission).collect(Collectors.toSet()));
        }
        return permissions;
    }

    public List<SmartRiceRole> findAll() {
        return roleMapper.findAll();
    }
}
