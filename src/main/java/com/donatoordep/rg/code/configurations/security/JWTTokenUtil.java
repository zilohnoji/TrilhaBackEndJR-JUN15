package com.donatoordep.rg.code.configurations.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.donatoordep.rg.code.entities.User;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class JWTTokenUtil {

    private final String ISSUER = "Código Certo API";
    private final String SECRET_KEY = "codigo-certo-secret-key-for-hmac2556";

    public String generateToken(User user) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getEmail())
                .withExpiresAt(generateExpirationToken())
                .sign(algorithm());
    }

    public String validateToken(String token) {
        return JWT.require(algorithm())
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Algorithm algorithm() {
        return Algorithm.HMAC256(SECRET_KEY);
    }

    public Instant generateExpirationToken() {
        return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
    }
}