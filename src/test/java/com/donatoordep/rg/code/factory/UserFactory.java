package com.donatoordep.rg.code.factory;

import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.util.ApplicationConfigIT;

import java.util.UUID;

public class UserFactory {

    private static final String NAME = "User Name";
    private static final String EMAIL = "user@gmail.com";
    private static final String PASSWORD = "$2b$12$6y0xWc7ZlNLj0Lyz97/5B.sxv0xUzQH56Zw8yoHkHP/QP2Xh7q10G";
    private static final EmailCodeConfirmation CODE_CONFIRMATION = EmailCodeConfirmationFactory.createCode();
    private static final RoleName ROLE = RoleName.ROLE_USER;

    public static User createUser() {
        return User.UserBuilder.builder()
                .name(NAME)
                .email(EMAIL)
                .password(PASSWORD)
                .role(ROLE)
                .code(CODE_CONFIRMATION)
                .build();
    }

    public static User persist(UserRepository repository, User entity) {
        return repository.save(entity);
    }
}