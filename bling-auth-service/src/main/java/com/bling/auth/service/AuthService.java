package com.bling.auth.service;
import com.bling.auth.model.LoginRequest;
import com.bling.common.util.Result;

public interface AuthService {
    Result<String> login(LoginRequest request);
}
