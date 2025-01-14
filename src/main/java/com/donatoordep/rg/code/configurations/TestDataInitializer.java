package com.donatoordep.rg.code.configurations;

import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Collections;
import java.util.List;

@Configuration
@Profile("test")
public class TestDataInitializer {

    private final UserRepository userRepository;

    @Autowired
    public TestDataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loadUserTest();
    }

    public void loadUserTest() {
        User activeUser = User.UserBuilder.builder()
                .name("Test Profile")
                .password("$2a$12$MtE9CrE3NPFXOj2c0SJC9e8k12f3ntvN81I32AMKoJ7VmNty9Lv1W")
                .email("test@gmail.com")
                .role(RoleName.ROLE_USER)
                .build().activeAccount();

        User inactiveUser = User.UserBuilder.builder()
                .name("Test Profile Inactive")
                .password("$2a$12$MtE9CrE3NPFXOj2c0SJC9e8k12f3ntvN81I32AMKoJ7VmNty9Lv1W")
                .email("testInactive@gmail.com")
                .role(RoleName.ROLE_USER)
                .build();

        this.userRepository.saveAll(List.of(activeUser, inactiveUser));
    }
}