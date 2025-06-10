package com.bling.permission.controller;

import com.bling.common.util.Result;
import com.bling.permission.model.SysPermission;
import com.bling.permission.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/tree")
    public Result<List<SysPermission>> tree() {
        return Result.ok(permissionService.buildTree());
    }

    @PostMapping("/addPermission")
    public Result<?> create(@RequestBody SysPermission permission) {
        permissionService.save(permission);
        return Result.ok("添加成功");
    }

    @PutMapping("/updatePermission")
    public Result<?> update(@RequestParam Long id, @RequestBody SysPermission permission) {
        permission.setId(id);
        permissionService.update(permission);
        return Result.ok("更新成功");
    }

    @DeleteMapping("/deletePermission")
    public Result<?> delete(@RequestParam Long id) {
        permissionService.delete(id);
        return Result.ok("删除成功");
    }
}
