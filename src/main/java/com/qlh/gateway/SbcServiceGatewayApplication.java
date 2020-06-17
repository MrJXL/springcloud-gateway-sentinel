package com.qlh.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@EnableEurekaClient
@SpringBootApplication
@Slf4j
public class SbcServiceGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbcServiceGatewayApplication.class, args);
        log.info("=====================启动完成=========================");
    }

}

