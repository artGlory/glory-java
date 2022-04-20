package com.spring.serviceProvider.module.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class MainController {

    @Value("${spring.application.name}")
    private String serviceName;
    @Value("${server.port}")
    private String servicePort;

    @GetMapping("/")
    public String star(HttpServletRequest httpServletRequest) {
        return serviceName + ":" + servicePort + " successful startup! ";
    }
}
