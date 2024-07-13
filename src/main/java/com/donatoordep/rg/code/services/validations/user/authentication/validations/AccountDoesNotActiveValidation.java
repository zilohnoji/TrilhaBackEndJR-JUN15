package com.donatoordep.rg.code.services.validations.user.authentication.validations;

import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBAccountDoesNotActiveException;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationArgs;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationValidation;
import org.springframework.stereotype.Component;

@Component
public class AccountDoesNotActiveValidation implements UserAuthenticationValidation {

    @Override
    public void validate(UserAuthenticationArgs args) {
        User entity = args.repository().findByEmail(args.dto().getEmail()).orElseThrow(ONBEntityNotFoundException::new);
        if (!entity.isEnabled()){
            throw new ONBAccountDoesNotActiveException();
        }
    }
}
