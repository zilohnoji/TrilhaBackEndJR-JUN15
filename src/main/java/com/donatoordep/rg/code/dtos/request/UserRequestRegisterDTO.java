package com.donatoordep.rg.code.dtos.request;

import com.donatoordep.rg.code.aop.jpaFieldValidator.annotations.JpaFieldlValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestRegisterDTO {

    @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
    private String name;

    @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
    @Email(message = "{UserRequestRegisterDTO.Email}")
    @JpaFieldlValidator(unique = true)
    private String email;

    @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
    @Size(min = 8, message = "{UserRequestRegisterDTO.Size}")
    private String password;

    public UserRequestRegisterDTO() {
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}