package com.spring.serviceConsume;

import com.spring.serviceConsume.config.CommonFeignConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableEurekaClient
@EnableFeignClients(defaultConfiguration = CommonFeignConfig.class)
@SpringBootApplication
public class ServiceConsumeApplication {

    public static void main(String[] args) {


        SpringApplication.run(ServiceConsumeApplication.class, args);
    }
}
