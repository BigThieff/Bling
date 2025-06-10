package com.bling.auth.service.impl;

import com.bling.auth.client.RemotePermissionClient;
import com.bling.auth.mapper.SysUserAuthMapper;
import com.bling.auth.model.LoginRequest;
import com.bling.auth.model.SysUser;
import com.bling.auth.service.AuthService;
import com.bling.common.util.JwtUtils;
import com.bling.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private SysUserAuthMapper sysUserAuthMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RemotePermissionClient remotePermissionClient;

    @Override
    public Result<String> login(LoginRequest request) {
        SysUser user = sysUserAuthMapper.findByUsername(request.getUsername());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return Result.fail("用户名或密码错误");
        }
        // 生成 JWT Token
        String token = JwtUtils.createToken(user.getId(), user.getUsername());

        // 调用 permission-service 获取权限
        Result<List<Long>> result = remotePermissionClient.getPermissions(user.getId());//todo modify response
        if (!result.isSuccess()) {
            return Result.fail("获取权限失败");
        }

        // 缓存权限
        cacheUserPermissionsToRedis(user.getId(), result.getData());

        return Result.ok(token);
    }

    private void cacheUserPermissionsToRedis(Long userId, List<Long> permissions) {
        String key = "bling:perm:user:" + userId;
        redisTemplate.delete(key); // 清除旧的权限
        if (permissions != null && !permissions.isEmpty()) {
            redisTemplate.opsForSet().add(key, permissions.toArray(new String[0]));
            redisTemplate.expire(key, Duration.ofHours(2)); // 设置过期时间
        }
    }
}
