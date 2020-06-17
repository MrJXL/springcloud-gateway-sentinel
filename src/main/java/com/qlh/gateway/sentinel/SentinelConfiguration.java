//package com.qlh.gateway.sentinel;
//
//
//import com.alibaba.csp.sentinel.adapter.gateway.common.SentinelGatewayConstants;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayFlowRule;
//import com.alibaba.csp.sentinel.adapter.gateway.common.rule.GatewayRuleManager;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.SentinelGatewayFilter;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
//import com.alibaba.csp.sentinel.adapter.gateway.sc.exception.SentinelGatewayBlockExceptionHandler;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.server.ServerResponse;
//import org.springframework.web.reactive.result.view.ViewResolver;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import javax.annotation.PostConstruct;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Configuration
//public class SentinelConfiguration {
//
//    private final List<ViewResolver> viewResolvers;
//    private final ServerCodecConfigurer serverCodecConfigurer;
//
//    public SentinelConfiguration(ObjectProvider<List<ViewResolver>> viewResolversProvider,
//                                 ServerCodecConfigurer serverCodecConfigurer) {
//        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
//        this.serverCodecConfigurer = serverCodecConfigurer;
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
//        // Register the block exception handler for Spring Cloud Gateway.
//        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public GlobalFilter sentinelGatewayFilter() {
//        return new SentinelGatewayFilter();
//    }
//
//    @PostConstruct
//    public void doInit() {
//        initGatewayRules();
//        initBlockHandler();
//    }
//
//    /**
//     * 初始化限流规则
//     */
//    private void initGatewayRules() {
//        /*GatewayFlowRule：网关限流规则，针对 API Gateway 的场景定制的限流规则，
//        可以针对不同 route 或自定义的 API 分组进行限流，
//        支持针对请求中的参数、Header、来源 IP 等进行定制化的限流。
//        */
//        Set<GatewayFlowRule> rules = new HashSet<>();
//        /*设置限流规则
//        resource: 资源名称，这里为路由router的ID
//        resourceMode: 路由模式
//        count: QPS即每秒钟允许的调用次数
//        intervalSec: 每隔多少时间统计一次汇总数据，统计时间窗口，单位是秒，默认是 1 秒。
//        */
//        rules.add(new GatewayFlowRule("zmg-sys")
//                .setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID)
//                .setCount(1).setIntervalSec(1));
//        GatewayRuleManager.loadRules(rules);
//    }
//
//    /**
//     * 自定义限流异常处理
//     * ResultSupport 为自定义的消息封装类，代码略
//     */
//    private void initBlockHandler() {
//        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
//            @Override
//            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange,
//                                                      Throwable throwable) {
//                return ServerResponse.status(HttpStatus.OK)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .body(BodyInserters.fromObject(
//                                ResultSupport.error("GW001", "系统访问量过大，限流啦!")));
//            }
//        });
//    }
//}
//
