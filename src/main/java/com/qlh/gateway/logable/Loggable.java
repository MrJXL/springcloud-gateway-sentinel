package com.qlh.gateway.logable;

import java.lang.annotation.*;

/**
 * @Classname Loggable
 * @Description <p> </p>
 * @Author JiangXiLiang
 * @Date 2020/5/25
 * @Version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Loggable {

    /**
     * 日志类型
     */
    String logType() default LogTypeConstant.LOG_TYPE_BZL;

}