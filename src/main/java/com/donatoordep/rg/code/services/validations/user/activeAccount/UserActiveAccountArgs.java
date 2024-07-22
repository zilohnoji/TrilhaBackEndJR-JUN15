package com.donatoordep.rg.code.services.validations.user.activeAccount;


import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;

public record UserActiveAccountArgs(String token, EmailCodeConfirmationRepository repository) {
}
