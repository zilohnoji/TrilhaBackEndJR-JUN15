package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.builders.dtos.response.UserResponseGetProfileInfoDTOSpecificationBuilder;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.mappers.entities.TaskMapper;

import java.util.List;
import java.util.UUID;

public class UserResponseGetProfileInfoDTO {

    private UUID identifier;
    private String name;
    private String email;
    private List<TaskResponseGetAllDTO> tasks;

    public static class UserResponseGetProfileInfoDTOBuilder implements UserResponseGetProfileInfoDTOSpecificationBuilder {

        private UserResponseGetProfileInfoDTO dto;

        private UserResponseGetProfileInfoDTOBuilder() {
            this.reset();
        }

        public static UserResponseGetProfileInfoDTOBuilder builder() {
            return new UserResponseGetProfileInfoDTOBuilder();
        }

        @Override
        public UserResponseGetProfileInfoDTO build() {
            return this.dto;
        }

        @Override
        public UserResponseGetProfileInfoDTOSpecificationBuilder identifier(UUID id) {
            this.dto.setIdentifier(id);
            return this;
        }

        @Override
        public UserResponseGetProfileInfoDTOSpecificationBuilder name(String name) {
            this.dto.setName(name);
            return this;
        }

        @Override
        public UserResponseGetProfileInfoDTOSpecificationBuilder email(String email) {
            this.dto.setEmail(email);
            return this;
        }

        @Override
        public UserResponseGetProfileInfoDTOSpecificationBuilder tasks(List<Task> tasks) {
            this.dto.setTasks(tasks.stream().map(TaskMapper::toResponse).toList());
            return this;
        }

        @Override
        public void reset() {
            this.dto = new UserResponseGetProfileInfoDTO();
        }
    }

    private void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setTasks(List<TaskResponseGetAllDTO> tasks) {
        this.tasks = tasks;
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