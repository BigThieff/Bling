package com.bling.user.service;


import com.bling.user.model.SysUser;

import java.util.List;

public interface UserService {
    List<SysUser> findAll();
    SysUser findById(Long id);
    void save(SysUser user);
    void update(SysUser user);
    void delete(Long id);
}
