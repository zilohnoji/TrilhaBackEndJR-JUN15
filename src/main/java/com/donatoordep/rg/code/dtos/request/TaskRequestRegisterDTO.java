package com.donatoordep.rg.code.dtos.request;

import jakarta.validation.constraints.NotBlank;

public class TaskRequestRegisterDTO {

    @NotBlank(message = "Campo requerido")
    private String title;

    @NotBlank(message = "Campo requerido")
    private String content;

    @NotBlank(message = "Campo requerido")
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