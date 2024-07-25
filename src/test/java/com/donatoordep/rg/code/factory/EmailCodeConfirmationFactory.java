package com.donatoordep.rg.code.factory;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmailCodeConfirmationFactory {


    public static EmailCodeConfirmation createCode() {
        return EmailCodeConfirmation.createCodeConfirmation(LocalDateTime.now().plusMinutes(30), 32);
    }

    public static EmailCodeConfirmation createCode(LocalDateTime expiredAt) {
        return EmailCodeConfirmation.createCodeConfirmation(expiredAt, 32);
    }

    public static EmailCodeConfirmation persist(EmailCodeConfirmationRepository repository, EmailCodeConfirmation entity) {
        return repository.save(entity);
    }
}
