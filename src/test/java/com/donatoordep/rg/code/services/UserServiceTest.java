package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

@DisplayName("UserService - Service Class Test")
public class UserServiceTest extends ApplicationConfigTest {

    static User entity;
    static EmailCodeConfirmation code;

    @BeforeAll
    static void setupAll() {
        code = EmailCodeConfirmationFactory.createCode();
        entity = UserFactory.createUser(code);
    }

    @BeforeEach
    void setup() {
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(code.getCode())).thenReturn(code);

        Mockito.when(this.userRepository.save(entity)).thenReturn(entity);
        Mockito.when(this.userRepository.findByEmailCodeConfirmationOrThrowNotFound(entity.getCode().getCode())).thenReturn(entity);
    }

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            this.userService.activeAccount(entity.getCode().getCode());
        }, "Expected nothing exception, but throw exception");
    }
}