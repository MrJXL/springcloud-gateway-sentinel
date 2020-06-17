package com.qlh.gateway.jwt;

import com.alibaba.fastjson.JSONObject;
import com.qlh.gateway.logable.LogTypeConstant;
import com.qlh.gateway.logable.LogUtil;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


/**
 * @Classname JwtTokenFilter
 * @Description <p>token拦截器 </p>
 * @Author JiangXiLiang
 * @Date 2020/5/19
 * @Version 1.0
 */
@Configuration
public class JwtTokenFilter implements GlobalFilter, Ordered {



    @Autowired
    private JwtTokenCacheUtil tokenCacheUtil;

    @Autowired
    private LogUtil logUtil;

    @Override
    public int getOrder() {
        return 1;
    }

    /**
     * 拉取token过期时间
     * @param expire
     */
    @Value("${token.expire:43200000}")
    @RefreshScope
    public void setExpire(Long expire) {
        if (expire != null) {
            JwtConstant.JWT_REFRESH_TTL = Long.valueOf(expire);
        }
    }
    /**
     * 过滤器business
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logUtil.info("测试注解info",LogTypeConstant.LOG_TYPE_BZL);
        logUtil.warn("测试注解warn",LogTypeConstant.LOG_TYPE_BZL);
        logUtil.error("测试注解error",LogTypeConstant.LOG_TYPE_ACCEL);
        //LoggerUtil.warn("测试Util");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 特殊路径直接跳出，如login跳过filter
        if(request.getURI().toString().contains("hello")){
            return chain.filter(exchange);
        }
        String userId = getLoginUserId(exchange);
        if (StringUtils.isNotBlank(userId)) {
            exchange.getResponse().getHeaders().set("isLogin", userId);
        } else {
//            String token = JwtFactory.createJWT("张三","11001");
//            tokenCacheUtil.saveToken(token,11001);

            exchange.getResponse().getHeaders().set("isLogin", "-1");
            JSONObject message = new JSONObject();
            message.put("statusCode", -1);
            message.put("msg", "请先登录");
            byte[] bits = message.toJSONString().getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(bits);
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //指定编码
            response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
            return response.writeWith(Mono.just(buffer));
        }
        return chain.filter(exchange);
    }

    /**
     * token-->userID
     * @param exchange
     * @return
     */
    private String getLoginUserId(ServerWebExchange exchange) {
        String userId = null;
        //String auth = exchange.getRequest().getQueryParams().getFirst("Authorization");
        String auth =exchange.getRequest().getHeaders().getFirst("Authorization");
        if ((auth != null) && (auth.length() > 7)) {
            Claims claims = JwtFactory.parseJWT(auth);
            if (claims != null) {
                userId = claims.get("userid") == null ? "" : claims.get("userid").toString();
                if (!tokenCacheUtil.checkTokenExists(auth, Integer.valueOf(userId))) {
                    return null;
                }
                exchange.getAttributes().put("userid",userId);
            }
        }
        return userId;
    }


}