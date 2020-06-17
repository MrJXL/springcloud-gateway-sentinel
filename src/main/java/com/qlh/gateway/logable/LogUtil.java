package com.qlh.gateway.logable;

import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;


/**
 * @Classname LogUtil
 * @Description <p>自定义日志工具类，用于采集特定格式的日志数据 </p>
 * @Author JiangXiLiang
 * @Date 2020/5/26
 * @Version 1.0
 */
@Component
public class LogUtil {

    @Loggable
    private Logger logger;

    /**
     * 业务日志
     * @param format
     * @param arguments
     */
    public void info(String format,String logType,Object... arguments) {
        MDC.put("logType",logType);
        logger.info(format, arguments);
    }
    /**
     * 业务日志
     * @param msg
     * @param e
     */
    public void info(String msg, String logType,Throwable e) {
        MDC.put("logType",logType);
        logger.warn(msg, e);
    }

    /**
     * 业务日志
     * @param format
     * @param arguments
     */
    public void debug(String format, String logType,Object... arguments) {
        MDC.put("logType",logType);
        logger.debug(format, arguments);
    }
    /**
     * 业务日志
     * @param msg
     * @param e
     */
    public void debug(String msg, String logType,Throwable e) {
        MDC.put("logType",logType);
        logger.debug(msg, e);
    }

    /**
     * 业务日志
     * @param format
     * @param arguments
     */
    public void warn(String format, String logType,Object... arguments) {
        MDC.put("logType",logType);
        logger.warn(format, arguments);
    }
    /**
     * 业务日志
     * @param msg
     * @param e
     */
    public void warn(String msg, String logType,Throwable e) {
        MDC.put("logType",logType);
        logger.warn(msg, e);
    }

    /**
     * 业务日志
     * @param format
     * @param arguments
     */
    public void error(String format, String logType,Object... arguments) {
        MDC.put("logType",logType);
        logger.error(format, arguments);
    }
    /**
     * error whth exception
     * @param msg
     * @param e
     */
    public void error(String msg, String logType,Throwable e) {
        MDC.put("logType",logType);
        logger.error(msg, e);
    }
}