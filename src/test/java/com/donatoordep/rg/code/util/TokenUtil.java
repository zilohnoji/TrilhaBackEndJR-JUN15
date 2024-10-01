package com.donatoordep.rg.code.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.donatoordep.rg.code.entities.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public abstract class TokenUtil {

    private static final String ISSUER = "Código Certo API Test";
    private static final String SECRET_KEY = "codigo-certo-not-secret-key-for-unit-test";
    private static final Instant EXPIRED_AT = LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));

    public static String generateJwtToken(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getEmail())
                .withExpiresAt(EXPIRED_AT)
                .sign(Algorithm.HMAC256(SECRET_KEY));
    }
}