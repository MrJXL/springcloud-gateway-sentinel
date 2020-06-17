package com.qlh.gateway.resolver;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Classname CommonKeyResolver
 * @Description <p>根据指定属性限流 ，采用令牌桶算法</p>
 * @Author JiangXiLiang
 * @Date 2020/5/22
 * @Version 1.0
 */
@Component
@Configuration
public class CommonKeyResolver implements KeyResolver {

    /**
     * 根据 Host 限流
     * @param exchange
     * @return
     */
    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

    /**
     * 根据请求的 uri 限流
     * @param exchange
     * @return
     */
//    @Override
//    public Mono<String> resolve(ServerWebExchange exchange) {
//        return Mono.just(exchange.getRequest().getURI().getPath());
//    }

    /**
     * 根据 user 限流
     * @param exchange
     * @return
     */
//    @Override
//    public Mono<String> resolve(ServerWebExchange exchange) {
//        return Mono.just(exchange.getRequest().getQueryParams().getFirst("user"));
//    }
}