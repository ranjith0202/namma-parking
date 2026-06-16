package com.park.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    RequestInterceptor internalFeignInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("X-GATEWAY", "true");
        };
    }
}

