package com.donatoordep.rg.code.repositories.impl;

import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

@DisplayName("UserRepository - Service Class Test")
public class UserRepositoryTest extends ApplicationConfigTest {

    User user;

    @BeforeEach
    void setup() {
        this.user = UserFactory.createUser();
    }

    @Test
    void findByEmailOrThrowNotFoundShouldReturnUserWhenEmailHasExists() {
        Mockito.when(this.userRepository.findByEmailOrThrowNotFound(ArgumentMatchers.anyString())).thenReturn(user);

        Assertions.assertDoesNotThrow(() -> {
            this.userRepository.findByEmailOrThrowNotFound("user@gmail.com");
        }, "Expected nothing exception, but throw exception");
    }
}