package com.spring.serviceConsume.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/actuator").authenticated() //拦截的url
                .antMatchers("/actuator/*").authenticated() //拦截的url
                .anyRequest().permitAll() //其他的请求全部放行
                .and()
                .httpBasic();        // 开启基本账密校验

    }

}