package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestAuthenticationDTO {

    @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
    @Email(message = "{UserRequest.Email}")
    private String email;

    @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
    @Size(min = 8, message = "{UserRequestAuthenticationDTO.Size}")
    private String password;

    public UserRequestAuthenticationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}