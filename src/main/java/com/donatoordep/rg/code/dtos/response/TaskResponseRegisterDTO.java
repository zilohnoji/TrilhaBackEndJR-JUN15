package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.builders.dtos.response.TaskResponseRegisterDTOSpecificationBuilder;

import java.time.Instant;
import java.util.UUID;

public class TaskResponseRegisterDTO {

    private UUID identifier;
    private String issuer;
    private Instant createdAt;

    private TaskResponseRegisterDTO() {
    }

    public static class TaskResponseRegisterDTOBuilder implements TaskResponseRegisterDTOSpecificationBuilder {

        private TaskResponseRegisterDTO dto;

        private TaskResponseRegisterDTOBuilder() {
            this.reset();
        }

        public static TaskResponseRegisterDTOBuilder builder() {
            return new TaskResponseRegisterDTOBuilder();
        }

        @Override
        public TaskResponseRegisterDTO build() {
            return this.dto;
        }

        @Override
        public TaskResponseRegisterDTOSpecificationBuilder identifier(UUID identifier) {
            this.dto.setIdentifier(identifier);
            return this;
        }

        @Override
        public TaskResponseRegisterDTOSpecificationBuilder issuer(String issuer) {
            this.dto.setIssuer(issuer);
            return this;
        }

        @Override
        public TaskResponseRegisterDTOSpecificationBuilder createdAt(Instant createdAt) {
            this.dto.setCreatedAt(createdAt);
            return this;
        }

        @Override
        public void reset() {
            this.dto = new TaskResponseRegisterDTO();
        }
    }

    private void setIdentifier(UUID identifier) {
        this.identifier = identifier;
    }

    private void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    private void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getIssuer() {
        return issuer;
    }

    public UUID getIdentifier() {
        return identifier;
    }
}