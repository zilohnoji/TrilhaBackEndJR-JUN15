package com.donatoordep.rg.code.builders.dtos.response;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;

import java.time.LocalDate;
import java.util.Date;

public interface UserResponseAuthenticationDTOSpecificationBuilder extends Builder<UserResponseAuthenticationDTO> {

    UserResponseAuthenticationDTOSpecificationBuilder email(String email);

    UserResponseAuthenticationDTOSpecificationBuilder token(String token);

    UserResponseAuthenticationDTOSpecificationBuilder expiredAt(String expiredAt);
}