package com.donatoordep.rg.code.mappers.dto.response;

import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;

public class UserResponseDTOMapper {

    public static UserResponseRegisterDTO toResponse(User entity) {
        return UserResponseRegisterDTO.UserResponseRegisterDTOBuilder.builder()
                .identifier(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}