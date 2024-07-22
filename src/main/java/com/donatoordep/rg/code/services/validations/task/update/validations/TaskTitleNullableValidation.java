package com.donatoordep.rg.code.services.validations.task.update.validations;

import com.donatoordep.rg.code.services.validations.task.update.TaskUpdateArgs;
import com.donatoordep.rg.code.services.validations.task.update.TaskUpdateValidation;
import org.springframework.stereotype.Component;

@Component
public class TaskTitleNullableValidation implements TaskUpdateValidation {

    @Override
    public void validate(TaskUpdateArgs args) {
        if (!(args.dto().title() == null)) {
            args.entity().setTitle(args.dto().title());
        }
    }
}