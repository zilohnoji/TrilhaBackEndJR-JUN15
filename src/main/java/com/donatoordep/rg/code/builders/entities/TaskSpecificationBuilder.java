package com.donatoordep.rg.code.builders.entities;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.TaskStatus;

import java.util.UUID;

public interface TaskSpecificationBuilder extends Builder<Task> {

    TaskSpecificationBuilder title(String title);

    TaskSpecificationBuilder content(String content);

    TaskSpecificationBuilder status(TaskStatus status);

    TaskSpecificationBuilder user(User user);

    TaskSpecificationBuilder id(UUID id);
}