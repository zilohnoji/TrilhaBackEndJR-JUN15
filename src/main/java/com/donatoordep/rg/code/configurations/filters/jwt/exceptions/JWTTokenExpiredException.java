package com.donatoordep.rg.code.configurations.filters.jwt.exceptions;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.donatoordep.rg.code.configurations.filters.jwt.exceptions.base.JWTExceptionSpecification;


public class JWTTokenExpiredException implements JWTExceptionSpecification {

    private static String ERROR = "token has expired on";
    private static Integer STATUS = 403;
    private static String PATH;

    public JWTTokenExpiredException(DecodedJWT jwt, String path) {
        ERROR += String.format(" %s", jwt.getExpiresAt().toString());
        PATH = path;
    }

    @Override
    public Integer getStatus() {
        return STATUS;
    }

    @Override
    public String getError() {
        return ERROR;
    }

    @Override
    public String getPath() {
        return PATH;
    }
}