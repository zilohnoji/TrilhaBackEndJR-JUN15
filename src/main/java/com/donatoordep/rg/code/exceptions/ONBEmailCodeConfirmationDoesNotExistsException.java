package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBEmailCodeConfirmationDoesNotExistsException extends RuntimeException implements ONBExceptionSpecification {

    private static final String ERROR = "email code confirmation does not exists";
    private static final Integer STATUS = 404;

    public ONBEmailCodeConfirmationDoesNotExistsException() {
        super(ERROR);
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}