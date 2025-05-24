package com.bling.user.service.impl;

import com.bling.auth.model.SysUser;
import com.bling.user.mapper.SysUserMapper;
import com.bling.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public List<SysUser> findAll() {
        return sysUserMapper.findAll();
    }

    public SysUser findById(Long id) {
        return sysUserMapper.findById(id);
    }

    public void save(SysUser user) {
        sysUserMapper.insert(user);
    }

    public void update(SysUser user) {
        sysUserMapper.update(user);
    }

    public void delete(Long id) {
        sysUserMapper.delete(id);
    }
}
