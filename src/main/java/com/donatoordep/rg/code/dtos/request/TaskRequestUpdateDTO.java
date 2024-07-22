package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record TaskRequestUpdateDTO(

        @NotNull(message = "{TaskRequestUpdateDTO.NotNull}")
        UUID id,

        String title,

        String content,

        String status
) {
}