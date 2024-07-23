package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;

@DisplayName("UserService - Service Class Test")
public class UserServiceTest extends ApplicationConfigTest {

    User entity;
    EmailCodeConfirmation code;
    EmailCodeConfirmation codeExpired;

    @BeforeEach
    void setup() {
        code = EmailCodeConfirmationFactory.createCode();
        codeExpired = EmailCodeConfirmationFactory.createCode(LocalDateTime.now().minusDays(10));
        entity = UserFactory.createUser(code);

        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(code.getCode())).thenReturn(code);
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(codeExpired.getCode())).thenReturn(codeExpired);

        Mockito.when(this.userRepository.save(entity)).thenReturn(entity);
        Mockito.when(this.userRepository.findByEmailCodeConfirmationOrThrowNotFound(entity.getCode().getCode())).thenReturn(entity);
        Mockito.when(this.userRepository.findByEmailCodeConfirmationOrThrowNotFound(codeExpired.getCode())).thenReturn(entity);
    }

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        Assertions.assertDoesNotThrow(() -> {
            this.userService.activeAccount(entity.getCode().getCode());
        }, "Expected nothing exception, but throw exception");
    }

//    @Test
//    void activeAccountShouldThrowTokenDoesNotExistsWhenTokenNotIsValid() {
//        Assertions.assertThrows(ONBEmailCodeConfirmationExpiredException.class, () -> {
//            this.userService.activeAccount(codeExpired.getCode());
//        });
//    }
}