package com.donatoordep.rg.code.builders.dtos.response;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.Task;

import java.util.List;
import java.util.UUID;

public interface UserResponseGetProfileInfoDTOSpecificationBuilder extends Builder<UserResponseGetProfileInfoDTO> {

    UserResponseGetProfileInfoDTOSpecificationBuilder identifier(UUID id);

    UserResponseGetProfileInfoDTOSpecificationBuilder name(String name);

    UserResponseGetProfileInfoDTOSpecificationBuilder email(String email);

    UserResponseGetProfileInfoDTOSpecificationBuilder tasks(List<Task> tasks);
}