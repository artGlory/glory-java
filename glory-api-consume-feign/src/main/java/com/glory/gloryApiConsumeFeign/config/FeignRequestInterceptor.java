package com.glory.gloryApiConsumeFeign.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * feign请求拦截器
 */
public class FeignRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
    }
}