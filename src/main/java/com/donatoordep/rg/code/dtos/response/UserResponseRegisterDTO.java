package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.builders.dtos.response.UserResponseRegisterDTOSpecificationBuilder;

import java.util.UUID;

public class UserResponseRegisterDTO {

    private UUID identifier;
    private String name;
    private String email;

    private UserResponseRegisterDTO() {
    }

    public static class UserResponseRegisterDTOBuilder implements UserResponseRegisterDTOSpecificationBuilder {

        private UserResponseRegisterDTO dto;

        private UserResponseRegisterDTOBuilder() {
            this.reset();
        }

        @Override
        public UserResponseRegisterDTO build() {
            return this.dto;
        }

        public static UserResponseRegisterDTOBuilder builder() {
            return new UserResponseRegisterDTOBuilder();
        }

        @Override
        public UserResponseRegisterDTOSpecificationBuilder identifier(UUID id) {
            this.dto.setIdentifier(id);
            return this;
        }

        @Override
        public UserResponseRegisterDTOSpecificationBuilder name(String name) {
            this.dto.setName(name);
            return this;
        }

        @Override
        public UserResponseRegisterDTOSpecificationBuilder email(String email) {
            this.dto.setEmail(email);
            return this;
        }

        @Override
        public void reset() {
            this.dto = new UserResponseRegisterDTO();
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

    public UUID getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}