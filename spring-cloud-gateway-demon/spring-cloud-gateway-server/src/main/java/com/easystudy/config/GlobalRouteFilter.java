package com.easystudy.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * 自定义GlobalFilter只要使用了@Configuration注解或者配置为一个spring bean就会生效，不需要在RouteLocator中配置
 * @author Administrator
 */
@Configuration
public class GlobalRouteFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
        
        builder.header("GlobalFilter","GlobalFilter success");
        //chain.filter(exchange.mutate().request(builder.build()).build());
        
        return chain.filter(exchange.mutate().request(builder.build()).build());
    }
}