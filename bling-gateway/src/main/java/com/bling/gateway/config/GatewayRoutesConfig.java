package com.bling.gateway.config;

import com.bling.gateway.filter.JwtAuthGatewayFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRoutesConfig {

    private final JwtAuthGatewayFilter jwtAuthGatewayFilter;

    public GatewayRoutesConfig(JwtAuthGatewayFilter jwtAuthGatewayFilter) {
        this.jwtAuthGatewayFilter = jwtAuthGatewayFilter;
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/auth/**")
                        .filters(f -> f.filter(jwtAuthGatewayFilter)) // 添加 JWT 鉴权过滤器
                        .uri("http://localhost:8001")) // 注意这里的端口要与 auth-service 启动端口一致
                .route("user-service", r -> r.path("/users/**")
                        .filters(f -> f.filter(jwtAuthGatewayFilter)) // 添加 JWT 鉴权过滤器
                        .uri("http://localhost:8002")) // 注意这里的端口要与 auth-service 启动端口一致
                .build();
    }
}
