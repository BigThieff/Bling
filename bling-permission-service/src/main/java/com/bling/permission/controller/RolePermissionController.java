package com.bling.permission.controller;

import com.bling.common.util.Result;
import com.bling.permission.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role-permissions")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    @PostMapping("/assign")
    public Result<?> assignPermissions(@RequestParam Long roleId, @RequestBody List<Long> permissionIds) {
        rolePermissionService.assignPermissions(roleId, permissionIds);
        return Result.ok("分配成功");
    }

    @GetMapping("/getRolePermissions")
    public Result<List<Long>> getPermissions(@RequestParam Long roleId) {
        return Result.ok(rolePermissionService.getPermissionIds(roleId));
    }
}
