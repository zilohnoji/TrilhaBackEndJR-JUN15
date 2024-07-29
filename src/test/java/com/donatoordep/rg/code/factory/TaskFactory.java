package com.donatoordep.rg.code.factory;

import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.TaskStatus;
import com.donatoordep.rg.code.repositories.impl.TaskRepository;

import java.util.UUID;

public class TaskFactory {

    private static final String TITLE = "Task Title";
    private static final String CONTENT = "Task Content";
    private static final TaskStatus STATUS = TaskStatus.PROGRESS;

    public static Task createTask() {
        return Task.TaskBuilder.builder()
                .id(UUID.randomUUID())
                .title(TITLE)
                .content(CONTENT)
                .status(STATUS)
                .build();
    }

    public static Task createTask(User user) {
        return Task.TaskBuilder.builder()
                .id(UUID.randomUUID())
                .title(TITLE)
                .content(CONTENT)
                .status(STATUS)
                .user(user)
                .build();
    }

    public static Task persist(TaskRepository repository, Task entity) {
        return repository.save(entity);
    }
}