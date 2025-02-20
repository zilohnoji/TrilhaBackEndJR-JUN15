package com.donatoordep.rg.code.configurations;

import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;
import com.donatoordep.rg.code.enums.TaskStatus;
import com.donatoordep.rg.code.repositories.impl.TaskRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
public class TestDataInitializer {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public TestDataInitializer(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.loadUserTest();
    }

    public void loadUserTest() {
        User userForTest = User.UserBuilder.builder()
                .name("Test Profile")
                .password("$2a$12$MtE9CrE3NPFXOj2c0SJC9e8k12f3ntvN81I32AMKoJ7VmNty9Lv1W")
                .email("test@gmail.com")
                .role(RoleName.ROLE_USER)
                .build().activeAccount();

        User inactiveUserForTest = User.UserBuilder.builder()
                .name("Test Profile")
                .password("$2a$12$MtE9CrE3NPFXOj2c0SJC9e8k12f3ntvN81I32AMKoJ7VmNty9Lv1W")
                .email("test2@gmail.com")
                .role(RoleName.ROLE_USER)
                .build();

        this.userRepository.saveAll(List.of(userForTest, inactiveUserForTest));

        Task taskForTest = Task.TaskBuilder.builder()
                .user(userForTest)
                .title("Task Title")
                .content("Task Content")
                .status(TaskStatus.PROGRESS)
                .build();

        this.taskRepository.save(taskForTest);
    }
}