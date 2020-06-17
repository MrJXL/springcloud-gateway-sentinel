package com.qlh.gateway.hystrix;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname JwtTokenFilter
 * @Description <p>熔断触发跳转 </p>
 * @Author JiangXiLiang
 * @Date 2020/5/22
 * @Version 1.0
 */
@RestController
public class FallbackController {

    @RequestMapping("/fallback")
    public String fallback() {
        return "Gateway fallback";
    }

}
