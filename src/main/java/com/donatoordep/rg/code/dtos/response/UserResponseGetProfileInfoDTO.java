package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.entities.User;

import java.util.List;
import java.util.UUID;

public class UserResponseGetProfileInfoDTO {

    private UUID identifier;
    private String name;
    private String email;
    private List<TaskResponseGetAllDTO> tasks;

    public UserResponseGetProfileInfoDTO(User entity) {
        this.identifier = entity.getId();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.tasks = entity.getTasks().stream().map(TaskResponseGetAllDTO::new).toList();
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<TaskResponseGetAllDTO> getTasks() {
        return tasks;
    }
}
