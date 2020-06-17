package com.qlh.gateway.jwt;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

/**
 * @Classname JwtFactory
 * @Description <p> JWT生成token</p>
 * @Author JiangXiLiang
 * @Date 2020/5/22
 * @Version 1.0
 */
public class JwtFactory {

    /**
     * 生成SecretKey
     * @return
     */
    private static SecretKey generalKey() {
        String stringKey = "eda1782204cf41efaca1e051ccc610be62acdcf24c09f011f343583c41cfb941f";
        byte[] encodedKey = Base64.encode(stringKey.getBytes()).getBytes();
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析jwt
     * @param jsonWebToken
     * @return
     */
    public static Claims parseJWT(String jsonWebToken) {
        try {
            SecretKey key = generalKey();
            Claims claims = (Claims) Jwts.parser().setSigningKey(key).parseClaimsJws(jsonWebToken).getBody();
            return claims;
        } catch (Exception var3) {
            return null;
        }
    }

    /**
     * 创建jwt
     * @param name
     * @param userId
     * @return
     */
    public static String createJWT(String name, String userId) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey signingKey = generalKey();
        JwtBuilder builder = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .claim("unique_name", name)
                .claim("userid", userId)
                .setIssuer("pc.api.yobtc.com")
                .setAudience("098f6bcd4621d373cade4e832627b4f6")
                .setIssuedAt(now).signWith(signatureAlgorithm, signingKey);
        if (JwtConstant.JWT_REFRESH_TTL >= 0L) {
            long expMillis = nowMillis + JwtConstant.JWT_REFRESH_TTL;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }

        return builder.compact();
    }


}