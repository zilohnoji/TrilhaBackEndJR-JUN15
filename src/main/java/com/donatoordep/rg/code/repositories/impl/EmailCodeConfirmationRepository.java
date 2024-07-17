package com.donatoordep.rg.code.repositories.impl;

import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.repositories.EmailCodeConfirmationSpecification;


public interface EmailCodeConfirmationRepository extends EmailCodeConfirmationSpecification {

    default com.donatoordep.rg.code.entities.EmailCodeConfirmation findByTokenOrThrowNotExists(String token) {
        return this.findByToken(token).orElseThrow(ONBEmailCodeConfirmationDoesNotExistsException::new);
    }

}