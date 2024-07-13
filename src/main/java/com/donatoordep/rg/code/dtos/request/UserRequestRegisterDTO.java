package com.donatoordep.rg.code.dtos.request;

import com.donatoordep.rg.code.services.validations.user.aop.annotations.JpaFieldlValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestRegisterDTO {

    @NotBlank(message = "Campo requerido")
    private String name;

    @NotBlank(message = "Campo Requerido")
    @Email(message = "Formato de email inválido")
    @JpaFieldlValidator(unique = true)
    private String email;

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve conter no minímo 8 caracteres")
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