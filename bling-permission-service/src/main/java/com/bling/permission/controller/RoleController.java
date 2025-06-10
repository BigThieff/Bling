package com.bling.permission.controller;

import com.bling.common.util.Result;
import com.bling.permission.model.SysRole;
import com.bling.permission.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/getAllRoles")
    public Result<List<SysRole>> list() {
        return Result.ok(roleService.findAll());
    }

    @PostMapping("/createRole")
    public Result<?> create(@RequestBody SysRole role) {
        roleService.save(role);
        return Result.ok("添加成功");
    }

    @PutMapping("/updateRole")
    public Result<?> update(@RequestParam Long id, @RequestBody SysRole role) {
        role.setId(id);
        roleService.update(role);
        return Result.ok("更新成功");
    }

    @DeleteMapping("/deleteRole")
    public Result<?> delete(@RequestParam Long id) {
        roleService.delete(id);
        return Result.ok("删除成功");
    }
}
