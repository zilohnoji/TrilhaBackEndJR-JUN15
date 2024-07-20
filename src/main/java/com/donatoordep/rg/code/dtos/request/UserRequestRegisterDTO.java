package com.donatoordep.rg.code.dtos.request;

import com.donatoordep.rg.code.aop.jpaFieldValidator.annotations.JpaFieldlValidator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestRegisterDTO(
        @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
        String name,

        @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
        @Email(message = "{UserRequestRegisterDTO.Email}")
        @JpaFieldlValidator(unique = true)
        String email,

        @NotBlank(message = "{UserRequestRegisterDTO.NotBlank}")
        @Size(min = 8, message = "{UserRequestRegisterDTO.Size}")
        String password
) {
}