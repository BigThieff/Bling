package com.bling.permission.service.impl;

import com.bling.permission.mapper.SysRolePermissionMapper;
import com.bling.permission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private SysRolePermissionMapper rolePermissionMapper;

    public void assignPermissions(Long roleId, List<Long> permissionIds) {
        rolePermissionMapper.deleteByRoleId(roleId);
        if (permissionIds != null && !permissionIds.isEmpty()) {
            rolePermissionMapper.insertBatch(roleId, permissionIds);
        }
    }

    public List<Long> getPermissionIds(Long roleId) {
        return rolePermissionMapper.findPermissionIdsByRoleId(roleId);
    }
}
