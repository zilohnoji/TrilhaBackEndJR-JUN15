package com.donatoordep.rg.code.exceptions;

import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;

public class ONBEnumTypeNotExistsException extends RuntimeException implements ONBExceptionSpecification {

    private static String ERROR = "enum type not exists";
    private static Integer STATUS = 400;

    public ONBEnumTypeNotExistsException() {
        super(ERROR);
    }

    public ONBEnumTypeNotExistsException(String existsTypes) {
        super(String.format("%s, exists types: %s", ERROR, existsTypes));
    }

    public String getError() {
        return ERROR;
    }

    public Integer getStatus() {
        return STATUS;
    }
}