package com.donatoordep.rg.code.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Informações para autenticação de um usuário cadastrado")
public record UserRequestAuthenticationDTO(
        @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
        @Email(message = "{UserRequest.Email}")
        String email,

        @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
        @Size(min = 8, message = "{UserRequestAuthenticationDTO.Size}")
        String password
) {
}