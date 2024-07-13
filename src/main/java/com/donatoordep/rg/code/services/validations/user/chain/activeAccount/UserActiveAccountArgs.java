package com.donatoordep.rg.code.services.validations.user.chain.activeAccount;


import com.donatoordep.rg.code.repositories.EmailCodeConfirmationRepository;

public record UserActiveAccountArgs(String token, EmailCodeConfirmationRepository repository) {
}
