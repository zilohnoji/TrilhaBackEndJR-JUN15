package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBEmailCodeConfirmationExpiredException extends RuntimeException implements ONBExceptionSpecification {

    private static final String ERROR = "email code confirmation has expired";
    private static final Integer STATUS = 422;

    public ONBEmailCodeConfirmationExpiredException() {
        super(ERROR);
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}