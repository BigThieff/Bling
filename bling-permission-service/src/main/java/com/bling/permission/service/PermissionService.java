package com.bling.permission.service;

import com.bling.permission.model.SysPermission;

import java.util.List;

public interface PermissionService {
    List<SysPermission> findAll();

    List<SysPermission> buildTree();

    void save(SysPermission permission);

    void update(SysPermission permission);

    void delete(Long id);
}
