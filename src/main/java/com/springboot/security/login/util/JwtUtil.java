package com.springboot.security.login.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    static final String TOKEN_PREFIX = "Bearer ";

    private static long expirationTime;
    private static String secretKey;

    @Value("${jwt.token.expiration.in-ms}")
    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Value("${jwt.signing.key.secret}")
    public void setKey(String secretKey) { this.secretKey = secretKey; }


    public static String createJwtToken(String userName) {
        return JWT.create()
                .withSubject(userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
                .sign(Algorithm.HMAC512(secretKey));
    }

    public static DecodedJWT decodeJwtToken(String beararToken) {
        String token = beararToken.replace(TOKEN_PREFIX, "");

        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(secretKey))
                                    .build();
            DecodedJWT jwt = verifier.verify(token);

            Date now = new Date();
            if(now.getTime() > jwt.getExpiresAt().getTime()) {
                return null;
            }
            if(jwt.getSubject() == null) {
                return null;
            }
            return jwt;
        } catch (JWTVerificationException exception){
            return null;
        }
    }
}
