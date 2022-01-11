package com.glory.gloryControllerEntrance.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 操作日志记录
 */
@Slf4j
@Aspect
@Component
public class OperateLogAspect {

    @Pointcut("@annotation(com.glory.gloryUtils.anno.OperateLog)")
    public void logPointCut() {
    }

    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 设置方法名称
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            String operateMethod = className + "." + methodName;
            //参数
            Object[] args = joinPoint.getArgs();
            StringBuilder sb = new StringBuilder();
            for (Object obj : args) {
                sb.append(obj.getClass().getSimpleName());
                sb.append("[");
                sb.append(obj);
                sb.append("]");
            }
            String params = sb.toString();
            //请求结果
            boolean resultFlag = true;
            if (e != null) {//请求异常
                resultFlag = false;
            }
            // TODO: 2021-11-03 保存用户操作日志 
        } catch (Exception exp) {
            log.error("保存操作日志异常:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

}