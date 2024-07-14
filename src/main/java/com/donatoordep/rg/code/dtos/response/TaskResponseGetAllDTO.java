package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.entities.Task;

public class TaskResponseGetAllDTO {

    private String title;
    private String content;
    private String status;

    public TaskResponseGetAllDTO(Task entity) {
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.status = entity.getContent();
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