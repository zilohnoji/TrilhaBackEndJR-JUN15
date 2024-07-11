package com.donatoordep.rg.code.dtos.response;

import java.util.UUID;

public class UserResponseRegisterDTO {

    private UUID identifier;
    private String name;
    private String email;

    public UserResponseRegisterDTO(UUID identifier, String name, String email) {
        this.identifier = identifier;
        this.name = name;
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