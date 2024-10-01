package com.donatoordep.rg.code.aop.jpaFieldValidator.specifications;

public interface FieldValidatorSpecification {

    boolean isValid(Object value);

    RuntimeException getException();
}