package com.donatoordep.rg.code.mappers.entities;

import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;

public class UserMapper {

    public static User toEntity(UserRequestRegisterDTO request) {
        return User.UserBuilder.builder()
                .name(request.getName())
                .email(request.getEmail())
                .role(RoleName.ROLE_USER)
                .build();
    }

    public static UserResponseRegisterDTO toResponse(User entity) {
        return UserResponseRegisterDTO.UserResponseRegisterDTOBuilder.builder()
                .identifier(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}