package com.bling.permission.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysRolePermissionMapper {
    void deleteByRoleId(Long roleId);

    void insertBatch(@Param("roleId") Long roleId, @Param("permissionIds") List<Long> permissionIds);

    List<Long> findPermissionIdsByRoleId(Long roleId);
}
