package com.donatoordep.rg.code.factory;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

public class EmailCodeConfirmationFactory {


    public static EmailCodeConfirmation createCode() {
        EmailCodeConfirmation code = EmailCodeConfirmation.createCodeConfirmation(LocalDateTime.now().plusMinutes(30), 32);
        code.setId(UUID.randomUUID());
        return code;
    }

    public static EmailCodeConfirmation createCode(LocalDateTime expiredAt) {
        EmailCodeConfirmation code = EmailCodeConfirmation.createCodeConfirmation(expiredAt, 32);
        code.setId(UUID.randomUUID());
        return code;
    }

    public static EmailCodeConfirmation persist(EmailCodeConfirmationRepository repository, EmailCodeConfirmation entity) {
        return repository.save(entity);
    }
}
