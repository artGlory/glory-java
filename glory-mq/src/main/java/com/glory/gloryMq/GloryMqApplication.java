package com.glory.gloryMq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class GloryMqApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryMqApplication.class, args);
    }

}
