package com.donatoordep.rg.code.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Informações para atualização dos dados de uma tarefa")
public record TaskRequestUpdateDTO(

        @NotNull(message = "{TaskRequestUpdateDTO.NotNull}")
        UUID id,

        String title,

        String content,

        String status
) {
}