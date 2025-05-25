package com.bling.auth.client;

import com.bling.common.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "permission-service")
public interface RemotePermissionClient {

    @GetMapping("/api/role-permissions/getRolePermissions")
    Result<List<String>> getPermissions(@RequestParam("userId") Long userId);
}
