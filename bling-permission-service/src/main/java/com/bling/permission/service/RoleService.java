package com.bling.permission.service;

import com.bling.permission.model.SysRole;

import java.util.List;

public interface RoleService {
    List<SysRole> findAll();

    void save(SysRole role);

    void update(SysRole role);

    void delete(Long id);
}
