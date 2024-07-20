package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.NotBlank;

public record TaskRequestRegisterDTO(
        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String title,

        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String content,

        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String status) {
}