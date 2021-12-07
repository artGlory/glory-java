package com.glory.gloryStorageMysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@ComponentScan(basePackages = {"com.glory"}
        , excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX
        , pattern = {"com.glory.gloryUtils.GloryUtilsApplication"
})
)
@SpringBootApplication
@MapperScan(basePackages = {"com.glory.gloryStorageMysql.dao"})
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.glory.gloryStorageMysql.dao"})
public class GloryStorageMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloryStorageMysqlApplication.class, args);
    }

}
