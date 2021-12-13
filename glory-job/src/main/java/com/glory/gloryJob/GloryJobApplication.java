package com.glory.gloryJob;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages = {"com.glory"}
        , excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX
        , pattern = {"com.glory.gloryStorageMysql.controller.*"
        , "com.glory.gloryStorageMysql.GloryStorageMysqlApplication"
        , "com.glory.gloryUtils.GloryUtilsApplication"
        , "com.glory.gloryCacheRedis.GloryCacheRedisApplication"
})
)
@SpringBootApplication
public class GloryJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryJobApplication.class, args);
    }

}
