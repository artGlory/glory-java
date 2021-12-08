package com.glory.gloryControllerEntrance.config;

import com.glory.gloryControllerEntrance.filter.logFilter.LogFilter;
import com.glory.gloryControllerEntrance.filter.xssFilter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfig {

    @Bean
    public Filter xssFilter() {
        return new XssFilter();
    }

    @Bean
    public Filter uuidFilter() {
        return new LogFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean20() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(xssFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(20);//order的数值越小 则优先级越高
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean30() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(uuidFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setOrder(30);
        return filterRegistrationBean;
    }


}

