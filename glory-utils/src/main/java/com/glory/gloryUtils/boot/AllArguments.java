package com.glory.gloryUtils.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@Order(value = 1)
public class AllArguments implements CommandLineRunner {

    /**
     * 程序启动参数
     * 对应IDEA中的 Program arguments
     */
    public static ApplicationArguments application_arguments;
    /**
     * 系统配置变量
     * 1.当前激活的是哪个配置文件
     * 2.获取application.yml里面的配置信息
     */
    public static AbstractEnvironment application_variables;
    /**
     * 环境变量
     */
    public static Map<String, String> environment_variables;

    @Autowired
    public AllArguments(ApplicationArguments applicationArguments, AbstractEnvironment abstractEnvironment) {
        application_arguments = applicationArguments;
        application_variables = abstractEnvironment;
        environment_variables = System.getenv();
    }

    @Override
    public void run(String... args) throws Exception {
        StringBuffer stringBuffer1 = new StringBuffer("\r\n");
        environment_variables.forEach((k, v) -> {
            stringBuffer1.append("[System environment variable]" + k + "=" + v);
            stringBuffer1.append("\r\n");
        });
        log.info(stringBuffer1.toString());

        StringBuffer stringBuffer2 = new StringBuffer("\r\n" + "[Active file]");
        for (String str : application_variables.getActiveProfiles()) {
            stringBuffer2.append(str + " ");
        }
        log.info(stringBuffer2.toString());

        StringBuffer stringBuffer3 = new StringBuffer("\r\n" + "[Program arguments]");
        // 获取传递给应用程序的原始未处理参数，所有的参数
        for (String sourceArg : application_arguments.getSourceArgs()) {
            stringBuffer3.append(sourceArg + " ");
        }
        log.info(stringBuffer3.toString());

    }

    /**
     * 获取当前激活的配置文件名字
     * 例如：dev   prod   test
     *
     * @return
     */
    public static String[] getActiveProfileName() {
        return application_variables.getActiveProfiles();
    }
}
