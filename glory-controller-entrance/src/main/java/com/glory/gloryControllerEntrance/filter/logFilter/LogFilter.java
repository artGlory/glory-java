package com.glory.gloryControllerEntrance.filter.logFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glory.gloryUtils.constants.SysConstants;
import com.glory.gloryUtils.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 请求异常的时候，log日志；记录所有入参；配合GlobalExceptionHandler使用；
 * LogFilter记录入参， GlobalExceptionHandler记录异常；两者有相同uuid
 */
@Slf4j
public class LogFilter implements Filter {
    private static Map<Integer, String> log_flag_info = new HashMap<Integer, String>();

    static {
        log_flag_info.put(0, "不记录请求参数日志");
        log_flag_info.put(1, "记录请求参数日志");
        log_flag_info.put(2, "当请求失败的时候，记录请求参数日志");
    }

    private int log_flag = 0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("【Filter】(uuid) init ; 当前日志级别： " + log_flag_info.get(log_flag));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        LogWrappedRequest logWrappedRequest = new LogWrappedRequest(httpServletRequest);//getReader() has already been called for this request
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        Map<String, Object> requestMap = null;
        if (logWrappedRequest.getAttribute(SysConstants.http_request_uuid) == null) {
            requestMap = new HashMap<>();
            requestMap.put("headers", HttpUtil.getHeaders(logWrappedRequest));
            requestMap.put("parameters", HttpUtil.getParameters(logWrappedRequest));
//            requestMap.put("attributes", HttpUtil.getAttributes(httpServletRequest));
            requestMap.put("requestBody", HttpUtil.getRequestBody(logWrappedRequest));

            String time = ZonedDateTime.now().toString();
            String uuid = UUID.randomUUID().toString();
            logWrappedRequest.setAttribute(SysConstants.http_request_uuid, uuid);
            logWrappedRequest.setAttribute(SysConstants.http_request_time, time);
            requestMap.put(SysConstants.http_request_uuid, logWrappedRequest.getAttribute(SysConstants.http_request_uuid));
            requestMap.put(SysConstants.http_request_time, logWrappedRequest.getAttribute(SysConstants.http_request_time));
            requestMap.put("RUI", logWrappedRequest.getRequestURI());
            requestMap.put("IP", HttpUtil.getRemoteAddr(logWrappedRequest));
            requestMap.put("UUID", uuid);

            httpServletResponse.setHeader(SysConstants.http_request_uuid, uuid);
        }

        chain.doFilter(logWrappedRequest, httpServletResponse);

        httpServletResponse.setHeader(SysConstants.http_response_time, ZonedDateTime.now().toString());

        switch (log_flag) {
            case 0:
                break;
            case 1:
                log.info("[请求参数][IP:{}][UUID:{}] {}",
                        requestMap.get("IP"), requestMap.get("IP"),
                        new ObjectMapper().writeValueAsString(requestMap));
                break;
            case 2:
                if (httpServletResponse.getHeader(SysConstants.success_response) != null
                        && httpServletResponse.getHeader(SysConstants.success_response).equals("false")) {
                    log.info("[请求参数][IP:{}][UUID:{}] {}",
                            requestMap.get("IP"), requestMap.get("IP"),
                            new ObjectMapper().writeValueAsString(requestMap));
                }
                break;
            default:
                break;
        }


    }

    @Override
    public void destroy() {
        log.info("【Filter】(uuid) destroy");
    }
}
