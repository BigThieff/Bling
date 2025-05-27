package com.bling.gateway.filter;

import com.bling.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Set;

@Component
public class JwtAuthGatewayFilter implements GatewayFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 登录接口不拦截
        if (path.contains("/auth/login")) {
            return chain.filter(exchange);
        }

        // 获取 Authorization 头部
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);
        try {
            Claims claims = JwtUtils.parseToken(token);
            Long userId = Long.valueOf(claims.get("userId").toString());

            // 获取 Redis 权限码
            String key = "bling:perm:user:" + userId;
            Set<String> permissionCodes = redisTemplate.opsForSet().members(key);

            // 可扩展：获取当前请求的方法和路径，判断是否匹配权限码
            // 示例仅做打印，不做限制
            HttpMethod httpMethod = exchange.getRequest().getMethod();
            String method = httpMethod != null ? httpMethod.name() : "UNKNOWN";
            String requestPath = exchange.getRequest().getPath().value();
            System.out.println("用户请求：" + method + " " + requestPath);
            System.out.println("权限码列表：" + permissionCodes);

            // TODO: 可在此加入权限路径 + 方法的匹配逻辑（高级权限校验）

            // 将 userId/username 放入请求头，传递给后续服务
            ServerHttpRequest mutatedRequest = exchange.getRequest()
                    .mutate()
                    .header("userId", userId.toString())
                    .header("username", claims.get("username").toString())
                    .build();

            return chain.filter(exchange.mutate().request(mutatedRequest).build());
        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -1; // 优先级最高
    }
}


