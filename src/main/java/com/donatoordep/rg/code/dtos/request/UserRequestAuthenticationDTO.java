package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestAuthenticationDTO(
        @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
        @Email(message = "{UserRequest.Email}")
        String email,

        @NotBlank(message = "{UserRequestAuthenticationDTO.NotBlank}")
        @Size(min = 8, message = "{UserRequestAuthenticationDTO.Size}")
        String password
) {
}