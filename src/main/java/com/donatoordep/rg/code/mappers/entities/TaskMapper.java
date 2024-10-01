package com.donatoordep.rg.code.mappers.entities;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.TaskStatus;

import java.time.Instant;

public class TaskMapper {

    public static Task toEntity(TaskRequestRegisterDTO request) {
        return Task.TaskBuilder.builder()
                .title(request.title())
                .content(request.content())
                .status(TaskStatus.valueOfOrThrowNotExists(request.status()))
                .build();
    }

    public static TaskResponseRegisterDTO toResponse(Task entity, User issuer) {
        return TaskResponseRegisterDTO.TaskResponseRegisterDTOBuilder.builder()
                .identifier(entity.getId())
                .issuer(issuer.getId().toString())
                .createdAt(Instant.now())
                .build();
    }

    public static TaskResponseGetAllDTO toResponse(Task entity) {
        return TaskResponseGetAllDTO.TaskResponseGetAllDTOBuilder.builder()
                .identifier(entity.getId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .status(entity.getStatus().toString())
                .build();
    }
}