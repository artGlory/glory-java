package com.glory.gloryControllerEntrance.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Order(Integer.MAX_VALUE)
public class InitApplication implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.err.println("<<<<<<<<<<<<<<<<<<<程序启动成功>>>>>>>>>>>>>>>>>>>");
    }
}
