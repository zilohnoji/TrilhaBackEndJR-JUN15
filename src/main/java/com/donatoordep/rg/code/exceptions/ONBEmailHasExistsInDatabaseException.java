package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBEmailHasExistsInDatabaseException extends RuntimeException implements ONBExceptionSpecification {

    private static final String ERROR = "email has exists in database";
    private static final Integer STATUS = 400;

    public ONBEmailHasExistsInDatabaseException() {
        super(ERROR);
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}