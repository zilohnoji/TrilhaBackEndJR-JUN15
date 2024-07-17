package com.donatoordep.rg.code.repositories.impl;

import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.repositories.UserSpecification;

import java.util.UUID;


public interface UserRepository extends UserSpecification {

    default com.donatoordep.rg.code.entities.User findByEmailOrThrowNotFound(String email) {
        return this.findByEmail(email).orElseThrow(ONBEntityNotFoundException::new);
    }

    default com.donatoordep.rg.code.entities.User findByEmailCodeConfirmationOrThrowNotFound(String code) {
        return this.findByEmailCodeConfirmation(code).orElseThrow(ONBEntityNotFoundException::new);
    }

    default com.donatoordep.rg.code.entities.User findByIdOrThrowNotFound(UUID id) {
        return this.findById(id).orElseThrow(ONBEntityNotFoundException::new);
    }
}