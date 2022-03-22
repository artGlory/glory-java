package com.glory.gloryApiConsumeFeign.config;

import feign.Contract;
import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-apiConsumeFeign.properties")
public class OpenFeignConfiguration {
    /**
     * NONE	不做任何记录
     * BASIC	仅记录请求方法和URL以及响应状态代码和执行时间
     * HEADERS	记录基本信息以及请求和响应标头
     * FULL	记录请求和响应的标题，正文和元数据
     * @return
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

}
