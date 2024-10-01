package com.donatoordep.rg.code.builders.entities;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;

import java.time.LocalDateTime;
import java.util.UUID;

public interface EmailCodeConfirmationSpecificationBuilder extends Builder<EmailCodeConfirmation> {

    EmailCodeConfirmationSpecificationBuilder code(String code);

    EmailCodeConfirmationSpecificationBuilder expiredAt(LocalDateTime expiredAt);
}