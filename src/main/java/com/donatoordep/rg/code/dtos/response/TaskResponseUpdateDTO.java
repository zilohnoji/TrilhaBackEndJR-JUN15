package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.entities.Task;

import java.time.Instant;
import java.util.UUID;

public class TaskResponseUpdateDTO {

    private UUID identifier;
    private UUID issuer;
    private Instant updateAt;

    public TaskResponseUpdateDTO(Task entity, UUID issuer) {
        this.identifier = entity.getId();
        this.issuer = issuer;
        this.updateAt = Instant.now();
    }

    public UUID getIdentifier() {
        return identifier;
    }

    public UUID getIssuer() {
        return issuer;
    }

    public Instant getUpdateAt() {
        return updateAt;
    }
}