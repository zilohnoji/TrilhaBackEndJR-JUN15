package com.donatoordep.rg.code.services.validations.user.authentication;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.repositories.UserRepository;

public record UserAuthenticationArgs(UserRequestAuthenticationDTO dto, UserRepository repository) {
}
