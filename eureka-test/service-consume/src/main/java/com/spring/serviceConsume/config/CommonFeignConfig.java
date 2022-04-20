package com.spring.serviceConsume.config;

import feign.*;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 如果在类上面加了@Configuration注解的话，就必须将该类放到@ComponentScan能扫描的包以外，否则这个类的配置将会被所有的FeignClient共享
 * 此处不添加@Configuration，在启动类上添加@EnableFeignClients(defaultConfiguration = CommonFeignConfig.class)，
 * feignClient如果使用特殊配置，添加 configuration = XxxFeignConfig.class
 */

public class CommonFeignConfig {

    /**
     * 日志级别	说明
     * NONE	    默认的，不显示任何日志
     * BASIC	仅记录请求方法、URL、响应状态码、执行时间
     * HEADERS	除了 BASIC 中定义的信息外，还有请求和响应的头信息
     * FULL	    除了 HEADERS 中定义的信息之外，还有请求和响应的正文及元数据
     *
     * @return
     */
    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    Retryer.Default retyrer() {
        // 重试间隔100 ms，最大重试间隔1s，最大重试次数默认5次
        return new Retryer.Default(100, SECONDS.toMillis(1), 5);
    }

    /**
     * 超时配置
     *
     * @return
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(10, TimeUnit.SECONDS, 60, TimeUnit.SECONDS, true);
    }

    /**
     * 编码器
     *
     * @return
     */
    @Bean
    public Encoder encoder() {
        return new Encoder.Default();
    }

    /**
     * 解码器
     *
     * @return
     */
    @Bean
    public Decoder decoder() {
        return new Decoder.Default();
    }


    /**
     * 在这个方法我们拿到了被拦截请求的RestTemplate实例，就可以往里面扔公共参数
     * Feign可以配置多个拦截器，但Spring并不保证其执行顺序。
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
//              requestTemplate.header("key","value");
            }
        };
        return requestInterceptor;
    }

}
