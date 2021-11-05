package com.glory.gloryControllerEntrance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.glory"})
@SpringBootApplication
public class GloryControllerEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryControllerEntranceApplication.class, args);
    }

}
