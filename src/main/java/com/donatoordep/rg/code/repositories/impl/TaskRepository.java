package com.donatoordep.rg.code.repositories.impl;

import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.exceptions.ONBEntityNotFoundException;
import com.donatoordep.rg.code.repositories.TaskSpecification;

import java.util.UUID;


public interface TaskRepository extends TaskSpecification {

    default Task findByIdOrThrowNotFound(UUID id) {
        return this.findById(id).orElseThrow(ONBEntityNotFoundException::new);
    }
}