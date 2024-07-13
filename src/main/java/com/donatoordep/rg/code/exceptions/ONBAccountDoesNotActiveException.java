package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBAccountDoesNotActiveException extends RuntimeException implements ONBExceptionSpecification {

    private static final String ERROR = "account not enabled";
    private static final Integer STATUS = 403;

    public ONBAccountDoesNotActiveException() {
        super(ERROR);
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}