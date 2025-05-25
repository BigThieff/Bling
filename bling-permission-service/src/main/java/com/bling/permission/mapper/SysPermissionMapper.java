package com.bling.permission.mapper;

import com.bling.permission.model.SysPermission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    List<SysPermission> findAll();

    SysPermission findById(Long id);

    int insert(SysPermission permission);

    int update(SysPermission permission);

    int delete(Long id);
}
