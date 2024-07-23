package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigIT;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;

@DisplayName("UserService - Service Class Integration Test")
public class UserServiceIT extends ApplicationConfigIT {

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        EmailCodeConfirmation codeEntity = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, EmailCodeConfirmationFactory.createCode());
        User userEntity = UserFactory.persist(userRepository, UserFactory.createUser(codeEntity).activeAccount());

        this.userService.activeAccount(userEntity.getCode().getCode());

        User activeEntity = this.userRepository.findByEmailOrThrowNotFound("user@gmail.com");

        Assertions.assertTrue(activeEntity.isEnabled(),
                () -> String.format("Expected enabled '%s', but return '%s'", true, activeEntity.isEnabled())
        );
    }

    @Test
    void registerShouldReturnUserResponseRegisterDTOWhenRequestDataIsValid() throws MessagingException, UnsupportedEncodingException {
        UserRequestRegisterDTO request = new UserRequestRegisterDTO("User Name", "user@gmail.com", "12345678");
        UserResponseRegisterDTO response = this.userService.register(request);

        User entity = this.userRepository.findByEmailOrThrowNotFound(request.email());

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(entity.getId(), response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", entity.getId(), response.getIdentifier())
        );
    }

    @Test
    void authenticationShouldReturnUserResponseAuthenticationDTOWhenRequestDataIsValid() {

        EmailCodeConfirmation codeEntity = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, EmailCodeConfirmationFactory.createCode());
        User userEntity = UserFactory.persist(userRepository, UserFactory.createUser(codeEntity).activeAccount());

        UserRequestAuthenticationDTO request = new UserRequestAuthenticationDTO(userEntity.getEmail(), "12345678");
        UserResponseAuthenticationDTO response = this.userService.authentication(request);

        Assertions.assertNotNull(response, "Expected a object, but return null");
        Assertions.assertNotNull(response.getToken(), "Expected a valid token, but return null");
        Assertions.assertEquals(userEntity.getEmail(), response.getEmail(),
                () -> String.format("Expected email '%s', but return '%s'", userEntity.getEmail(), response.getEmail())
        );
    }

    @Test
    void getUserProfileShouldReturnUserResponseGetProfileInfoDTOWhenUserIsAuthenticated() {
        EmailCodeConfirmation codeEntity = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, EmailCodeConfirmationFactory.createCode());
        User userEntity = UserFactory.persist(this.userRepository, UserFactory.createUser(codeEntity).activeAccount());
        Task taskEntity = TaskFactory.persist(this.taskRepository, TaskFactory.createTask(userEntity));

        userEntity.addTask(taskEntity);
        UserFactory.persist(this.userRepository, userEntity);

        UserResponseGetProfileInfoDTO response = this.userService.getUserProfile(userEntity);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(1, response.getTasks().size(),
                () -> String.format("Expected size 1, but return %s", response.getTasks().size())
        );

        Assertions.assertEquals(userEntity.getId(), response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", userEntity.getId(), response.getIdentifier())
        );
    }

//    @Nested
//    @DisplayName("UserService - Service Class Integration Fail Test")
//    class UserServiceITFail {
//    }
}