package com.spring.serviceConsume.feign.startTest;

import org.springframework.stereotype.Component;

@Component
public class StartTestFeignClientFallback implements StartTestFeignClient {
    @Override
    public String startUpTest(String authorization) {
        return null;
    }
}
