package com.donatoordep.rg.code.exceptions.base;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

    private List<FieldMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String error, String path) {
        super(status, error, path);
    }

    public void addError(String property, String message) {
        errors.add(new FieldMessage(property, message));
    }

    public List<FieldMessage> getErrors() {
        return errors;
    }
}