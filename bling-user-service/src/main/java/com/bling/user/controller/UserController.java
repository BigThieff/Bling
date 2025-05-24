package com.bling.user.controller;

import com.bling.auth.model.SysUser;
import com.bling.common.util.Result;
import com.bling.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getAllUser")
    public Result<List<SysUser>> listUsers() {
        return Result.ok(userService.findAll());
    }

    @GetMapping("/getUserById")
    public Result<SysUser> getUser(@RequestParam Long id) {
        return Result.ok(userService.findById(id));
    }

    @PostMapping("/createUser")
    public Result<?> createUser(@RequestBody SysUser user) {
        userService.save(user);
        return Result.ok("创建成功");
    }

    @PutMapping("/updateUser")
    public Result<?> updateUser(@RequestParam Long id, @RequestBody SysUser user) {
        user.setId(id);
        userService.update(user);
        return Result.ok("更新成功");
    }

    @DeleteMapping("deleteUserById")
    public Result<?> deleteUser(@RequestParam Long id) {
        userService.delete(id);
        return Result.ok("删除成功");
    }
}