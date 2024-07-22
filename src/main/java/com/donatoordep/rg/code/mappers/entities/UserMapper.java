package com.donatoordep.rg.code.mappers.entities;

import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;

public class UserMapper {

    public static User toEntity(UserRequestRegisterDTO request) {
        return User.UserBuilder.builder()
                .name(request.name())
                .email(request.email())
                .role(RoleName.ROLE_USER)
                .build();
    }

    public static UserResponseRegisterDTO toResponseForRegisterDto(User entity) {
        return UserResponseRegisterDTO.UserResponseRegisterDTOBuilder.builder()
                .identifier(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    public static UserResponseGetProfileInfoDTO toResponseForGetAllDto(User entity) {
        return UserResponseGetProfileInfoDTO.UserResponseGetProfileInfoDTOBuilder.builder()
                .identifier(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .tasks(entity.getTasks())
                .build();
    }
}