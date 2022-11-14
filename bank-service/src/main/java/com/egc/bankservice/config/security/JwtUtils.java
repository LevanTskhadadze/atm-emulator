package com.egc.bankservice.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.issuer}")
    private String issuer;
    public static final int EXPIRATION_TIME = 5;

    public String createAccessToken(String principal) {
        return JWT.create()
                  .withIssuer(issuer)
                  .withSubject(principal)
                  .withIssuedAt(new Date())
                  .withExpiresAt(getExpirationTime())
                  .sign(Algorithm.HMAC256(jwtKey.getBytes()));
    }

    private Date getExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, JwtUtils.EXPIRATION_TIME);
        return calendar.getTime();
    }
}
