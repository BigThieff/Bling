package com.bling.auth.mapper;

import com.bling.auth.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserAuthMapper {
    SysUser findByUsername(String username);
}
