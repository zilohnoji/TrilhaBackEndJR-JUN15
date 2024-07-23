package com.donatoordep.rg.code.factory;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;
import com.donatoordep.rg.code.repositories.impl.UserRepository;

import java.util.UUID;

public class UserFactory {

    private static final String NAME = "User Name";
    private static final String EMAIL = "user@gmail.com";
    private static final String PASSWORD = "$2a$12$koPD.5mB3eCDsxdDN/gTJOZde/uE3wcP0vkYQrZhz6YE47tzZ2wYe";
    private static final EmailCodeConfirmation CODE_CONFIRMATION = EmailCodeConfirmationFactory.createCode();
    private static final RoleName ROLE = RoleName.ROLE_USER;

    public static User createUser() {
        return User.UserBuilder.builder()
                .id(UUID.randomUUID())
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE)
                .code(CODE_CONFIRMATION)
                .build();
    }

    public static User createUser(UUID uuid) {
        return User.UserBuilder.builder()
                .id(uuid)
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE)
                .code(CODE_CONFIRMATION)
                .build();
    }

    public static User createUser(EmailCodeConfirmation code) {
        return User.UserBuilder.builder()
                .id(UUID.randomUUID())
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE)
                .code(code)
                .build();
    }

    public static User persist(UserRepository repository, User entity) {
        return repository.save(entity);
    }
}