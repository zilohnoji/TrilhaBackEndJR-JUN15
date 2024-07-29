package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.ExpiredTokenValidation;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.TokenDoesNotExistsValidation;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;

@DisplayName("UserService - Service Class Test")
class UserServiceTest extends ApplicationConfigTest {

    User entity;
    EmailCodeConfirmation code;
    EmailCodeConfirmation expiredCode;

    @BeforeEach
    void setupAll() {
        userService.setActiveAccountValidations(
                Arrays.asList(
                        new ExpiredTokenValidation(),
                        new TokenDoesNotExistsValidation()
                ));

        code = EmailCodeConfirmationFactory.createCode();
        entity = UserFactory.createUser(code);
        expiredCode = EmailCodeConfirmationFactory.createCode(LocalDateTime.now().minusDays(10));
    }

    @Test
    void registerShouldReturnUserResponseRegisterDTOWhenRequestValidData() throws MessagingException, UnsupportedEncodingException {
        Mockito.when(this.passwordEncoder.encode("12345678")).thenReturn("$2a$12$koPD.5mB3eCDsxdDN/gTJOZde/uE3wcP0vkYQrZhz6YE47tzZ2wYe");
        Mockito.when(this.userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(entity);

        UserRequestRegisterDTO request = new UserRequestRegisterDTO(entity.getName(), entity.getEmail(), "12345678");
        UserResponseRegisterDTO response = this.userService.register(request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertNotNull(response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", entity.getId(), response.getIdentifier())
        );

        Assertions.assertEquals(request.email(), response.getEmail(),
                () -> String.format("Expected email '%s', but return '%s'", entity.getEmail(), response.getEmail())
        );
    }

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        Mockito.when(this.userRepository.findByEmailCodeConfirmationOrThrowNotFound(entity.getCode().getCode())).thenReturn(entity);
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(code.getCode())).thenReturn(code);
        Mockito.when(this.userRepository.save(entity)).thenReturn(entity);

        Assertions.assertDoesNotThrow(() -> {
            this.userService.activeAccount(entity.getCode().getCode());
        }, "Expected nothing exception, but throw exception");
    }

    @Nested
    @DisplayName("Service Class Fail Test")
    class UserServiceFailTest {
        @Test
        void activeAccountShouldThrowEmailCodeConfirmationDoesNotExistsExceptionWhenTokenNotExists() {
            Mockito.when(emailCodeConfirmationRepository.findByTokenOrThrowNotExists("")).thenThrow(ONBEmailCodeConfirmationDoesNotExistsException.class);

            Assertions.assertThrows(ONBEmailCodeConfirmationDoesNotExistsException.class, () -> {
                userService.activeAccount("");
            });
        }

        @Test
        void activeAccountShouldThrowEmailCodeConfirmationExpiredExceptionWhenTokenHasExpired() {
            Mockito.when(emailCodeConfirmationRepository.findByTokenOrThrowNotExists(expiredCode.getCode())).thenReturn(expiredCode);

            Assertions.assertThrows(ONBEmailCodeConfirmationExpiredException.class, () -> {
                userService.activeAccount(expiredCode.getCode());
            });
        }
    }
}