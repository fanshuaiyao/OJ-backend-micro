package com.yupi.yuojbackenduserservice.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    private static final String SING = "!@#$$qwwretwsfwfwfwewvwvikbnorbnior3bn";

    /**
     * 生成token
     */
    public static String getToken(Map<String, String> claims) throws UnsupportedEncodingException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);

        JWTCreator.Builder builder = JWT.create();
        // playLoad
        claims.forEach((k,v) -> {
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(SING));
        return token;
    }


    /**
     * 验证token合法性  并返回信息
     */
    public static DecodedJWT verifyToken(String token) throws UnsupportedEncodingException {
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }




}
