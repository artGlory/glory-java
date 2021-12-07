package com.glory.gloryControllerEntrance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages = {"com.glory"}
        , excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX
        , pattern = {"com.glory.gloryStorageMysql.controller.*"
        , "com.glory.gloryStorageMysql.GloryStorageMysqlApplication"
        , "com.glory.gloryUtils.GloryUtilsApplication"
})
)
@SpringBootApplication
@MapperScan(basePackages = {"com.glory.gloryStorageMysql.dao"})
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.glory.gloryStorageMysql.dao"})
public class GloryControllerEntranceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryControllerEntranceApplication.class, args);
    }
}
