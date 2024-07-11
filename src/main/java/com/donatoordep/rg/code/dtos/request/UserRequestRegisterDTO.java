package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestRegisterDTO {

    @NotBlank(message = "Campo requerido")
    private String name;

    @NotBlank(message = "Campo Requerido")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank(message = "Campo requerido")
    @Size(min = 8, message = "A senha deve conter no minímo 8 caracteres")
    private String password;

    public UserRequestRegisterDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
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