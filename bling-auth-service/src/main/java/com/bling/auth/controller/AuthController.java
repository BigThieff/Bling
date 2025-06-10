package com.bling.auth.controller;

import com.bling.auth.model.LoginRequest;
import com.bling.auth.service.AuthService;
import com.bling.common.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
