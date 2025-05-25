package com.bling.permission.controller;

import com.bling.common.util.Result;
import com.bling.permission.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-roles")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/assign")
    public Result<?> assignRoles(@RequestParam Long userId, @RequestBody List<Long> roleIds) {
        userRoleService.assignRoles(userId, roleIds);
        return Result.ok("分配成功");
    }

    @GetMapping("/getUserRoles")
    public Result<List<Long>> getUserRoles(@RequestParam Long userId) {
        return Result.ok(userRoleService.getRoleIds(userId));
    }
}
