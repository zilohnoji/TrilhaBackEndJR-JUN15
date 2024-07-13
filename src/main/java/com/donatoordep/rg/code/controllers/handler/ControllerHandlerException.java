package com.donatoordep.rg.code.controllers.handler;

import com.donatoordep.rg.code.exceptions.ONBAccountDoesNotActiveException;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.exceptions.ONBEmailHasExistsInDatabaseException;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.exceptions.base.ONBExceptionSpecification;
import com.donatoordep.rg.code.exceptions.base.StandardError;
import com.donatoordep.rg.code.exceptions.base.ValidationError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerHandlerException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException exception, HttpServletRequest request) {
        ValidationError error = new ValidationError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "invalid data", request.getRequestURI());
        exception.getBindingResult().getFieldErrors().forEach(e -> error.addError(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }

    @ExceptionHandler(ONBEntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ONBEntityNotFoundException exception, HttpServletRequest request) {
        return this.createHandleException(exception, request.getRequestURI());
    }

    @ExceptionHandler(ONBEmailCodeConfirmationDoesNotExistsException.class)
    public ResponseEntity<StandardError> emailCodeConfirmationDoesNotExists(ONBEmailCodeConfirmationDoesNotExistsException exception, HttpServletRequest request) {
        return this.createHandleException(exception, request.getRequestURI());
    }

    @ExceptionHandler(ONBEmailHasExistsInDatabaseException.class)
    public ResponseEntity<StandardError> emailHasExistsInDatabase(ONBEmailHasExistsInDatabaseException exception, HttpServletRequest request) {
        return this.createHandleException(exception, request.getRequestURI());
    }

    @ExceptionHandler(ONBAccountDoesNotActiveException.class)
    public ResponseEntity<StandardError> accountDoesNotActive(ONBAccountDoesNotActiveException exception, HttpServletRequest request) {
        return this.createHandleException(exception, request.getRequestURI());
    }

    private ResponseEntity<StandardError> createHandleException(ONBExceptionSpecification e, String path) {
        return ResponseEntity.status(HttpStatus.valueOf(e.getStatus())).body(new StandardError(e.getStatus(), e.getError(), path));
    }
}