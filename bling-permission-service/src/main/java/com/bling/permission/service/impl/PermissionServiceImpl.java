package com.bling.permission.service.impl;

import com.bling.permission.mapper.SysPermissionMapper;
import com.bling.permission.model.SysPermission;
import com.bling.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private SysPermissionMapper permissionMapper;

    public List<SysPermission> findAll() {
        return permissionMapper.findAll();
    }

    public List<SysPermission> buildTree() {
        List<SysPermission> all = permissionMapper.findAll();
        Map<Long, SysPermission> map = new HashMap<>();
        List<SysPermission> root = new ArrayList<>();

        for (SysPermission p : all) {
            map.put(p.getId(), p);
        }

        for (SysPermission p : all) {
            if (p.getParentId() == null || p.getParentId() == 0) {
                root.add(p);
            } else {
                SysPermission parent = map.get(p.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) parent.setChildren(new ArrayList<>());
                    parent.getChildren().add(p);
                }
            }
        }
        return root;
    }

    public void save(SysPermission permission) {
        permissionMapper.insert(permission);
    }

    public void update(SysPermission permission) {
        permissionMapper.update(permission);
    }

    public void delete(Long id) {
        permissionMapper.delete(id);
    }
}
