package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.request.TaskRequestUpdateDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseGetAllDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseRegisterDTO;
import com.donatoordep.rg.code.dtos.response.TaskResponseUpdateDTO;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.services.validations.task.update.validations.TaskContentNullableValidation;
import com.donatoordep.rg.code.services.validations.task.update.validations.TaskStatusNullableValidation;
import com.donatoordep.rg.code.services.validations.task.update.validations.TaskTitleNullableValidation;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@DisplayName("TaskService - Service Class Test")
class TaskServiceTest extends ApplicationConfigTest {

    @InjectMocks
    TaskService taskService;

    User user;
    Task task;

    @BeforeEach
    void setup() {
        this.taskService.setUpdateValidations(Arrays.asList(
                new TaskContentNullableValidation(),
                new TaskStatusNullableValidation(),
                new TaskTitleNullableValidation()
        ));

        this.user = UserFactory.createUser();
        this.task = TaskFactory.createTask(user);

        this.user.addTask(task);
    }

    @Test
    void createShouldReturnTaskResponseRegisterDTOWhenRequestValidData() {
        Mockito.when(this.taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskRequestRegisterDTO request = new TaskRequestRegisterDTO(task.getTitle(), task.getContent(), task.getStatus().name());
        TaskResponseRegisterDTO response = this.taskService.create(user, request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(user.getId().toString(), response.getIssuer(),
                () -> String.format("Expected id '%s', but return '%s'", user.getId(), response.getIssuer())
        );
    }

    @Test
    void deleteByIdShouldDoNothingWhenTaskHasExists() {
        Mockito.doNothing().when(this.taskRepository).deleteByIdOrThrowNotFound(user.getId(), task.getId());

        Assertions.assertDoesNotThrow(() -> {
            this.taskService.deleteById(user, task.getId());
        }, "Expected nothing exception, but throw exception");
    }

    @Test
    void getTasksByUserShouldReturnPageOfTaskResponseGetAllDTOWhenUserHasAuthenticated() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("title"));

        Mockito.when(this.taskRepository.getTasksByUserId(user.getId(), pageRequest)).thenReturn(new PageImpl<>(List.of(task)));

        Page<TaskResponseGetAllDTO> response = this.taskService.getTasksByUser(user, pageRequest);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(1, response.getSize(),
                () -> String.format("Expected size 1, but return '%s'", response.getSize())
        );
    }

    @Test
    void updateShouldReturnTaskResponseUpdateDTOWhenTitleNotNull() {
        Mockito.when(this.taskRepository.findByIdOrThrowNotFound(task.getId())).thenReturn(task);
        Mockito.when(this.taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskRequestUpdateDTO request = new TaskRequestUpdateDTO(task.getId(), "Task Updated", null, null);
        TaskResponseUpdateDTO response = this.taskService.update(user, request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(request.id(), response.getIdentifier(),
                () -> String.format("Expected identifier '%s', but return '%s'", request.id(), response.getIdentifier())
        );
    }

    @Test
    void updateShouldReturnTaskResponseUpdateDTOWhenContentNotNull() {
        Mockito.when(this.taskRepository.findByIdOrThrowNotFound(task.getId())).thenReturn(task);
        Mockito.when(this.taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskRequestUpdateDTO request = new TaskRequestUpdateDTO(task.getId(), null, "Content Updated", null);
        TaskResponseUpdateDTO response = this.taskService.update(user, request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(request.id(), response.getIdentifier(),
                () -> String.format("Expected identifier '%s', but return '%s'", request.id(), response.getIdentifier())
        );
    }

    @Test
    void updateShouldReturnTaskResponseUpdateDTOWhenStatusNotNull() {
        Mockito.when(this.taskRepository.findByIdOrThrowNotFound(task.getId())).thenReturn(task);
        Mockito.when(this.taskRepository.save(ArgumentMatchers.any(Task.class))).thenReturn(task);

        TaskRequestUpdateDTO request = new TaskRequestUpdateDTO(task.getId(), null, null, "quit");
        TaskResponseUpdateDTO response = this.taskService.update(user, request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(request.id(), response.getIdentifier(),
                () -> String.format("Expected identifier '%s', but return '%s'", request.id(), response.getIdentifier())
        );
    }
}