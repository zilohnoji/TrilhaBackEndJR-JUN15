package com.donatoordep.rg.code.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Informações para cadastro de uma nova tarefa")
public record TaskRequestRegisterDTO(
        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String title,

        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String content,

        @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
        String status
) {
}