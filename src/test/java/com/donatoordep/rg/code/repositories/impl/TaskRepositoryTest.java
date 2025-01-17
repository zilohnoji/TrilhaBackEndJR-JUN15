package com.donatoordep.rg.code.repositories.impl;

import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.UUID;

@DisplayName("TaskRepository - Service Class Test")
public class TaskRepositoryTest extends ApplicationConfigTest {

    User user;
    Task task;

    @BeforeEach
    void setup() {
        this.user = UserFactory.createUser();
        this.task = TaskFactory.createTask(user);
    }

    @Nested
    public class TaskRepositoryTestFail {
        @Test
        void findByIdOrThrowNotFoundShouldReturnNotFoundExceptionWhenIdNotExists() {
            UUID id = UUID.randomUUID();

            Mockito.when(taskRepository.findByIdOrThrowNotFound(id)).thenReturn(task);
            Assertions.assertDoesNotThrow(() -> {
                taskRepository.findByIdOrThrowNotFound(id);
            }, "Expected nothing exception, but throw exception");
        }
    }
}
