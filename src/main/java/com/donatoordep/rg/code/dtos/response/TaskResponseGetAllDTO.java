package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.builders.dtos.response.TaskResponseGetAllDTOSpecificationBuilder;

import java.util.UUID;

public class TaskResponseGetAllDTO {

    private UUID identifier;
    private String title;
    private String content;
    private String status;

    private TaskResponseGetAllDTO() {
    }

    public static class TaskResponseGetAllDTOBuilder implements TaskResponseGetAllDTOSpecificationBuilder {

        private TaskResponseGetAllDTO dto;

        private TaskResponseGetAllDTOBuilder() {
            this.reset();
        }

        public static TaskResponseGetAllDTOBuilder builder() {
            return new TaskResponseGetAllDTOBuilder();
        }

        @Override
        public TaskResponseGetAllDTO build() {
            return this.dto;
        }

        @Override
        public TaskResponseGetAllDTOSpecificationBuilder identifier(UUID identifier) {
            this.dto.setIdentifier(identifier);
            return this;
        }

        @Override
        public TaskResponseGetAllDTOSpecificationBuilder title(String title) {
            this.dto.setTitle(title);
            return this;
        }

        @Override
        public TaskResponseGetAllDTOSpecificationBuilder content(String content) {
            this.dto.setContent(content);
            return this;
        }

        @Override
        public TaskResponseGetAllDTOSpecificationBuilder status(String status) {
            this.dto.setStatus(status);
            return this;
        }

        @Override
        public void reset() {
            this.dto = new TaskResponseGetAllDTO();
        }
    }

    private void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setStatus(String status) {
        this.status = status;
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