package com.bling.user.mapper;

import com.bling.auth.model.SysUser;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysUserMapper {
    SysUser findById(Long id);
    List<SysUser> findAll();
    int insert(SysUser user);
    int update(SysUser user);
    int delete(Long id);
}
