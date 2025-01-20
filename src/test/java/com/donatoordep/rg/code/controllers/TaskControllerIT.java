package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.TaskRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.request.TaskRequestUpdateDTO;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.TaskStatus;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigIT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TaskControllerIT extends ApplicationConfigIT {

    private User user;
    private Task task;

    @BeforeEach
    void setup() {
        user = UserFactory.createUser();
        task = TaskFactory.createTask(user);
        user.addTask(task);
    }

    @Test
    @WithUserDetails("test@gmail.com")
    void createShouldReturnTaskResponseRegisterDTOWhenRequestValidData() throws Exception {
        TaskRequestRegisterDTO request = new TaskRequestRegisterDTO("Estudar Java", "Estudar Java às 17:30", TaskStatus.COMPLETED.name());

        ResultActions result = mockMvc.perform(post("/tasks").accept(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(request)));

        result.andExpect(jsonPath("$.identifier").exists());
        result.andExpect(jsonPath("$.issuer").exists());
    }

    @Test
    @WithUserDetails("test@gmail.com")
    void getTasksByUserShouldReturnTaskResponseGetAllDTOWhenUserLogged() throws Exception {
        ResultActions result = mockMvc.perform(get("/tasks").accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"));

        result.andExpect(jsonPath("$.content[0].title").value("Task Title"));
        result.andExpect(jsonPath("$.content[0].content").value("Task Content"));
    }

    @Test
    @WithUserDetails("test@gmail.com")
    void deleteByIdShouldReturnNoContentDTOWhenUserLogged() throws Exception {
        User user = this.userRepository.findByEmailOrThrowNotFound("test@gmail.com");
        Task taskUser = user.getTasks().get(0);

        ResultActions result = mockMvc.perform(delete("/tasks/{taskId}", taskUser.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"));

        result.andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails("test@gmail.com")
    void updateShouldReturnTaskResponseUpdateDTOWhenUserLogged() throws Exception {
        User user = this.userRepository.findByEmailOrThrowNotFound("test@gmail.com");
        Task taskUser = user.getTasks().get(0);

        TaskRequestUpdateDTO request = new TaskRequestUpdateDTO(taskUser.getId(), "Task Title Updated",
                "Task Content Updated", TaskStatus.PROGRESS.name());

        ResultActions result = mockMvc.perform(post("/tasks/update", taskUser.getId()).accept(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(request)));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.identifier").exists());
        result.andExpect(jsonPath("$.issuer").value(user.getId().toString()));
    }
}
