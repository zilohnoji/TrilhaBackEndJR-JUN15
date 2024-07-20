package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.entities.Task;

import java.util.UUID;

public class TaskResponseGetAllDTO {

    private UUID identifier;
    private String title;
    private String content;
    private String status;

    public TaskResponseGetAllDTO(Task entity) {
        this.identifier = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.status = entity.getStatus().toString();
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }
}