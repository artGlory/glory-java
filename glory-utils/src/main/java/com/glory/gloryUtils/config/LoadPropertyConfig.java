package com.glory.gloryUtils.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:application-utils.properties")
public class LoadPropertyConfig {

}
