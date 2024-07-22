package com.donatoordep.rg.code.entities;

import com.donatoordep.rg.code.factory.UserFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User - Domain Class Behavior Test")
public class UserTest {

    @Test
    void activeAccountShouldActiveUserWhenUserNotIsActive() {

        User activeUser = UserFactory.createUser().activeAccount();

        Assertions.assertNotNull(activeUser,
                () -> String.format("Expected '%s', but return null", User.class)
        );

        Assertions.assertTrue(activeUser.isEnabled(),
                () -> String.format("Expected enabled '%s', but return '%s'", true, activeUser.isEnabled())
        );
    }
}