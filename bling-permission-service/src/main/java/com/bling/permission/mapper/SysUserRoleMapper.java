package com.bling.permission.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserRoleMapper {
    void deleteByUserId(Long userId);

    void insertBatch(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    List<Long> findRoleIdsByUserId(Long userId);
}
