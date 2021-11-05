package com.glory.gloryUtils.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 分布式任务锁
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JobLockAnnotation {
    /**
     * 任务名
     */
    String name() default "";

    /**
     * 任务间隔时间，在这个时间内分布式应用不能重复执行
     * 单位为秒
     */
    int duration();

}
