package com.qlh.gateway.jwt;

/**
 * @Classname JwtConstant
 * @Description <p> </p>
 * @Author JiangXiLiang
 * @Date 2020/5/22
 * @Version 1.0
 */
public class JwtConstant {

    /*
     * token Key
     */
    public static final String TOKEN_KEY_PERFIX = "AUTH_TOKEN_";

    /**
     * 拦截器返回值:token错误
     */
    public static final int ERROR_RESPONSE_TOKEN_CODE = 1000;

    public static final String ERROR_RESPONSE_AUTH_TOKEN_TIMEOUT = "token过期，请重新登录";

    public static final String ERROR_RESPONSE_AUTH_PARSE = "无效的token";

    public static final String JWT_ID = "098f6bcd4621d373cade4e832627b4f6";

    public static final String JWT_NAME = "pc.api.yobtc.com";

    public static final String JWT_SECRET = "eda1782204cf41efaca1e051ccc610be62acdcf24c09f011f343583c41cfb941f";

    public static final int JWT_TTL = 3600000;

    public static final int JWT_REFRESH_INTERVAL = 3300000;

    public static long JWT_REFRESH_TTL = 3600000L;


}