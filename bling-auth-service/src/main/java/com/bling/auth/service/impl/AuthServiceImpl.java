package com.bling.auth.service.impl;

import com.bling.auth.mapper.SysUserMapper;
import com.bling.auth.model.LoginRequest;
import com.bling.auth.model.SysUser;
import com.bling.auth.service.AuthService;
import com.bling.common.util.JwtUtils;
import com.bling.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Result<String> login(LoginRequest request) {
        SysUser user = sysUserMapper.findByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return Result.fail("用户名或密码错误");
        }
        String token = JwtUtils.createToken(user.getId(), user.getUsername());
        return Result.ok(token);
    }
}
