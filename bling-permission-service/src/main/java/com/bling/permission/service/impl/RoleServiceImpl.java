package com.bling.permission.service.impl;

import com.bling.permission.mapper.SysRoleMapper;
import com.bling.permission.model.SysRole;
import com.bling.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private SysRoleMapper roleMapper;

    public List<SysRole> findAll() {
        return roleMapper.findAll();
    }

    public void save(SysRole role) {
        roleMapper.insert(role);
    }

    public void update(SysRole role) {
        roleMapper.update(role);
    }

    public void delete(Long id) {
        roleMapper.delete(id);
    }
}
