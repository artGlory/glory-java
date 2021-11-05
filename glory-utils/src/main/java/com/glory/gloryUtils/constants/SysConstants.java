package com.glory.gloryUtils.constants;

import java.util.Arrays;
import java.util.List;

/**
 * 系统常量信息
 */
public class SysConstants {
    /**
     * 用于request,response  设置唯一标识
     */
    public static final String http_request_uuid = "http_request_uuid";
    /**
     * 用于http请求 设置时间
     */
    public static final String http_request_time = "http_request_time";
    /**
     * 用于http请求返回设置时间
     */
    public static final String http_response_time = "http_response_time";
    /**
     * 用户过滤器判断response是否异常
     */
    public static final String success_response = "successResponse";
    /**
     * 错误编码
     */
    public static List<Integer> http_error_code_list = Arrays.asList(405, 404, 403, 500, 501);
}
