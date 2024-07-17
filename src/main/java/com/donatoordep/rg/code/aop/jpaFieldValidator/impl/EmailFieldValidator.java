package com.donatoordep.rg.code.aop.jpaFieldValidator.impl;

import com.donatoordep.rg.code.aop.jpaFieldValidator.specifications.FieldValidatorSpecification;
import com.donatoordep.rg.code.exceptions.ONBEmailHasExistsInDatabaseException;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailFieldValidator implements FieldValidatorSpecification {

    private final UserRepository userRepository;

    @Autowired
    public EmailFieldValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(Object value) {
        return value instanceof String && userRepository.existsByEmail((String) value);
    }

    @Override
    public RuntimeException getException() {
        return new ONBEmailHasExistsInDatabaseException();
    }
}