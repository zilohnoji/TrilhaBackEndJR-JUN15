package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class TaskRequestRegisterDTO {

    @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
    private String title;

    @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
    private String content;

    @NotBlank(message = "{TaskRequestRegisterDTO.NotBlank}")
    private String status;

    public TaskRequestRegisterDTO() {
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