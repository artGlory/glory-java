package com.spring.serviceConsume.feign.startTest;

import com.spring.serviceConsume.config.CommonFeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SERVICE-PROVIDER", configuration = CommonFeignConfig.class, fallback = StartTestFeignClientFallback.class)
public interface StartTestFeignClient {

    @GetMapping(name = "/")
    String startUpTest(@RequestHeader("authorization") String authorization);
}
