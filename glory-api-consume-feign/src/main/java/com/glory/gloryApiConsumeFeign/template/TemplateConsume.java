package com.glory.gloryApiConsumeFeign.template;

import com.glory.gloryApiConsumeFeign.config.FeignRequestInterceptor;
import com.glory.gloryApiConsumeFeign.config.OpenFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "template", configuration = {OpenFeignConfiguration.class, FeignRequestInterceptor.class})
public interface TemplateConsume {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    String getMyIp();

}
