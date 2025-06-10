package com.bling.permission.mapper;

import com.bling.permission.model.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRoleMapper {
    List<SysRole> findAll();

    SysRole findById(Long id);

    int insert(SysRole role);

    int update(SysRole role);

    int delete(Long id);
}
