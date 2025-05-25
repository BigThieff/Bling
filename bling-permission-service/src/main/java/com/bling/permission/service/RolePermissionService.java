package com.bling.permission.service;

import java.util.List;

public interface RolePermissionService {
    void assignPermissions(Long roleId, List<Long> permissionIds);

    List<Long> getPermissionIds(Long roleId);
}
