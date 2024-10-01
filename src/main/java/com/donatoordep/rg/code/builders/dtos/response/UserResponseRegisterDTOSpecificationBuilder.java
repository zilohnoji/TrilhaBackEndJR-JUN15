package com.donatoordep.rg.code.builders.dtos.response;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;

import java.util.UUID;

public interface UserResponseRegisterDTOSpecificationBuilder extends Builder<UserResponseRegisterDTO> {

    UserResponseRegisterDTOSpecificationBuilder identifier(UUID id);

    UserResponseRegisterDTOSpecificationBuilder name(String name);

    UserResponseRegisterDTOSpecificationBuilder email(String email);
}