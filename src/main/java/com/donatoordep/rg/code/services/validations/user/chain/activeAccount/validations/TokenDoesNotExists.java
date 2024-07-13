package com.donatoordep.rg.code.services.validations.user.chain.activeAccount.validations;

import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountArgs;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountValidation;
import org.springframework.stereotype.Component;

@Component
public class TokenDoesNotExists implements UserActiveAccountValidation {

    @Override
    public void validate(UserActiveAccountArgs args) {
        args.repository().findByToken(args.token()).orElseThrow(ONBEmailCodeConfirmationDoesNotExistsException::new);
    }
}