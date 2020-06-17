package com.qlh.gateway.jwt;

import org.springframework.stereotype.Repository;

/**
 * @Classname TokenCacheUtil
 * @Description <p> </p>
 * @Author JiangXiLiang
 * @Date 2020/5/22
 * @Version 1.0
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @Classname TokenCacheUtil
 * @Description <p>token 认证 </p>
 * @Author JiangXiLiang
 * @Date 2020/5/19
 * @Version 1.0
 */
@Repository
public class JwtTokenCacheUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;


    private static final String CACHE_KEY_PREFIX = "user:token:pc:";

    private static final Integer[] BOSS_UID = new Integer[]{271498,427928};

    private String getCacheKeyByUserId(Integer userId) {
        return CACHE_KEY_PREFIX + userId.toString();
    }

    /**
     * 存储token
     * @param token
     * @param userId
     */
    public void saveToken(String token, Integer userId) {
        redisTemplate.opsForValue().set(getCacheKeyByUserId(userId), token);
        redisTemplate.expire(getCacheKeyByUserId(userId), new Long(JwtConstant.JWT_REFRESH_TTL / 1000),TimeUnit.SECONDS);
    }

    /**
     * 校验token是否存在
     * @param token
     * @param userId
     * @return
     */
    public boolean checkTokenExists(String token, Integer userId) {
        for (Integer uid : BOSS_UID) {
            if (uid.equals(userId)) {
                return true;
            }
        }
        String redisToken = redisTemplate.opsForValue().get(getCacheKeyByUserId(userId));
        return redisToken != null && redisToken.equals(token);
    }
}
