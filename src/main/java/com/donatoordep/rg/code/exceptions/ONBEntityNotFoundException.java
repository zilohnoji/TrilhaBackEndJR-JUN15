package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBEntityNotFoundException extends RuntimeException implements ONBExceptionSpecification {

    private static final String ERROR = "entity not found";
    private static final Integer STATUS = 404;

    public ONBEntityNotFoundException() {
        super(ERROR);
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}