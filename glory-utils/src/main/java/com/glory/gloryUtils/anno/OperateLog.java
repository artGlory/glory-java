package com.glory.gloryUtils.anno;

import java.lang.annotation.*;

/**
 * [ 用户操作日志 ]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperateLog {
    String value() default "";
}
