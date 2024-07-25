package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigIT;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.*;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@DisplayName("UserService - Service Class Integration Test")
public class UserServiceIT extends ApplicationConfigIT {

    static User validUser;
    static Task validTask;
    static EmailCodeConfirmation validCode;
    static EmailCodeConfirmation expiredCode;

    @BeforeAll
    static void setupAll() {
        validCode = EmailCodeConfirmationFactory.createCode();
        validUser = UserFactory.createUser(validCode);
        expiredCode = EmailCodeConfirmationFactory.createCode(LocalDateTime.now().minusDays(10));
    }

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        validCode = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, validCode);
        validUser = UserFactory.persist(this.userRepository, validUser);

        this.userService.activeAccount(validUser.getCode().getCode());

        User activeEntity = this.userRepository.findByEmailOrThrowNotFound("user@gmail.com");

        Assertions.assertTrue(activeEntity.isEnabled(),
                () -> String.format("Expected enabled '%s', but return '%s'", true, activeEntity.isEnabled())
        );
    }

    @Test
    void registerShouldReturnUserResponseRegisterDTOWhenRequestDataIsValid() throws MessagingException, UnsupportedEncodingException {
        UserRequestRegisterDTO request = new UserRequestRegisterDTO("User Name For Register", "user.register@gmail.com", "12345678");
        UserResponseRegisterDTO response = this.userService.register(request);

        User entity = this.userRepository.findByEmailOrThrowNotFound(request.email());

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(entity.getId(), response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", entity.getId(), response.getIdentifier())
        );
    }

    @Test
    void authenticationShouldReturnUserResponseAuthenticationDTOWhenRequestDataIsValid() {
        validCode = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, validCode);
        validUser = UserFactory.persist(this.userRepository, validUser.activeAccount());

        UserRequestAuthenticationDTO request = new UserRequestAuthenticationDTO(validUser.getEmail(), "12345678");
        UserResponseAuthenticationDTO response = this.userService.authentication(request);

        Assertions.assertNotNull(response, "Expected a object, but return null");
        Assertions.assertNotNull(response.getToken(), "Expected a valid token, but return null");
        Assertions.assertEquals(validUser.getEmail(), response.getEmail(),
                () -> String.format("Expected email '%s', but return '%s'", validUser.getEmail(), response.getEmail())
        );
    }

    @Test
    void getUserProfileShouldReturnUserResponseGetProfileInfoDTOWhenUserIsAuthenticated() {
        validCode = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, validCode);
        validUser = UserFactory.persist(this.userRepository, validUser.activeAccount());
        validTask = TaskFactory.persist(this.taskRepository, TaskFactory.createTask(validUser));

        validUser.addTask(validTask);

        UserResponseGetProfileInfoDTO response = this.userService.getUserProfile(validUser);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(1, response.getTasks().size(),
                () -> String.format("Expected size 1, but return %s", response.getTasks().size())
        );

        Assertions.assertEquals(validUser.getId(), response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", validUser.getId(), response.getIdentifier())
        );
    }

    @Nested
    @DisplayName("UserService - Service Class Integration Fail Test")
    class UserServiceITFail {

        @Test
        void activeAccountShouldThrowONBEmailCodeConfirmationExpiredExceptionWhenTokenHasExpired() {
            expiredCode = EmailCodeConfirmationFactory.persist(emailCodeConfirmationRepository, expiredCode);
            User userExpiredCode = UserFactory.persist(userRepository, UserFactory.createUser(expiredCode));

            Assertions.assertThrows(ONBEmailCodeConfirmationExpiredException.class, () -> {
                        userService.activeAccount(userExpiredCode.getCode().getCode());
                    }, "Expected throw ONBEmailCodeConfirmationExpiredException, but not throw exception"
            );
        }
    }
}