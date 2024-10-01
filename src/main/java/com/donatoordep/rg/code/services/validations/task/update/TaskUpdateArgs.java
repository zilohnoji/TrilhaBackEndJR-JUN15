package com.donatoordep.rg.code.services.validations.task.update;

import com.donatoordep.rg.code.dtos.request.TaskRequestUpdateDTO;
import com.donatoordep.rg.code.entities.Task;

public record TaskUpdateArgs(TaskRequestUpdateDTO dto, Task entity) {
}
