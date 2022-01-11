package com.glory.gloryControllerEntrance.filter.mdcFilter;

import com.glory.gloryUtils.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * MDC，动态日志头
 */
@Slf4j
public class MdcFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("【Filter】(MDC) init ; ");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        MDC.put("remoteIp", HttpUtil.getRemoteAddr(httpServletRequest));
        chain.doFilter(httpServletRequest, response);
        MDC.clear();
    }

    @Override
    public void destroy() {
        log.info("【Filter】(MDC) destroy");
    }
}
