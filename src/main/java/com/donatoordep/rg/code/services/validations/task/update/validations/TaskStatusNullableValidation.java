package com.donatoordep.rg.code.services.validations.task.update.validations;

import com.donatoordep.rg.code.enums.TaskStatus;
import com.donatoordep.rg.code.services.validations.task.update.TaskUpdateArgs;
import com.donatoordep.rg.code.services.validations.task.update.TaskUpdateValidation;
import org.springframework.stereotype.Component;

@Component
public class TaskStatusNullableValidation implements TaskUpdateValidation {

    @Override
    public void validate(TaskUpdateArgs args) {
        if (!(args.dto().status() == null)) {
            args.entity().setStatus(TaskStatus.valueOf(args.dto().status().toUpperCase()));
        }
    }
}
