package com.donatoordep.rg.code.services.validations.user.activeAccount.validations;

import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountArgs;
import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountValidation;
import org.springframework.stereotype.Component;

@Component
public class TokenDoesNotExistsValidation implements UserActiveAccountValidation {

    @Override
    public void validate(UserActiveAccountArgs args) {
        args.repository().findByTokenOrThrowNotExists(args.token());
    }
}