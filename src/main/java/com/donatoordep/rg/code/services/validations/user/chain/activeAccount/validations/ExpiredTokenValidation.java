package com.donatoordep.rg.code.services.validations.user.chain.activeAccount.validations;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountArgs;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountValidation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ExpiredTokenValidation implements UserActiveAccountValidation {

    @Override
    public void validate(UserActiveAccountArgs args) {
        EmailCodeConfirmation entity = args.repository().findByToken(args.token()).orElseThrow(ONBEmailCodeConfirmationDoesNotExistsException::new);
        if (entity.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new ONBEmailCodeConfirmationExpiredException();
        }
    }
}