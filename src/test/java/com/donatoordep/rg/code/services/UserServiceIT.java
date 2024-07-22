package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigIT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("UserService - Service Class Integration Test")
public class UserServiceIT extends ApplicationConfigIT {

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {

        // Arrange
        User inactiveUser = UserFactory.persist(userRepository, UserFactory.createUser());
        EmailCodeConfirmation userCode = EmailCodeConfirmationFactory.persist(emailCodeConfirmationRepository, inactiveUser.getCode());

        // Act
        this.userService.activeAccount(userCode.getCode());

        User activeUser = userRepository.findByEmailOrThrowNotFound("user@gmail.com");

        // Assert
        Assertions.assertTrue(activeUser.isEnabled(),
                () -> String.format("Expected enabled '%s', but return '%s'", true, activeUser.isEnabled())
        );
    }
}