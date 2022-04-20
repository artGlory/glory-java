package com.spring.serviceConsume.controller.main;

import com.spring.serviceConsume.feign.startTest.StartTestFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class MainController {

    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${server.port}")
    private String servicePort;

    @Autowired
    private StartTestFeignClient startTestFeignClient;

    @GetMapping("/")
    public Object star(HttpServletRequest httpServletRequest) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", serviceName + ":" + servicePort + " successful startup! ");
        String feignFuncationReturnInfo = startTestFeignClient.startUpTest("token");
        log.info(feignFuncationReturnInfo);
        resultMap.put("feignFuncationReturnInfo", feignFuncationReturnInfo);

        return resultMap;
    }
}
