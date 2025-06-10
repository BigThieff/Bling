package com.bling.permission.service.impl;

import com.bling.permission.mapper.SysUserRoleMapper;
import com.bling.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private SysUserRoleMapper userRoleMapper;

    public void assignRoles(Long userId, List<Long> roleIds) {
        userRoleMapper.deleteByUserId(userId);
        if (roleIds != null && !roleIds.isEmpty()) {
            userRoleMapper.insertBatch(userId, roleIds);
        }
    }

    public List<Long> getRoleIds(Long userId) {
        return userRoleMapper.findRoleIdsByUserId(userId);
    }
}
