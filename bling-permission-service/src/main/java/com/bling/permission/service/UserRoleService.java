package com.bling.permission.service;

import java.util.List;

public interface UserRoleService {
    void assignRoles(Long userId, List<Long> roleIds);

    List<Long> getRoleIds(Long userId);
}
