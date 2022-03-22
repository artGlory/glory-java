package com.glory.gloryApiConsumeFeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GloryApiConsumeFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryApiConsumeFeignApplication.class, args);
    }

}
