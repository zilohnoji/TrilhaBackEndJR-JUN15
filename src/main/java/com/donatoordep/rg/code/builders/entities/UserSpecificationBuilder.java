package com.donatoordep.rg.code.builders.entities;

import com.donatoordep.rg.code.builders.Builder;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.enums.RoleName;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserSpecificationBuilder extends Builder<User> {
    UserSpecificationBuilder id(UUID id);

    UserSpecificationBuilder name(String name);

    UserSpecificationBuilder email(String email);

    UserSpecificationBuilder password(String password);

<<<<<<< HEAD
    UserSpecificationBuilder code(String code, LocalDateTime expiredAt);
=======
    UserSpecificationBuilder code(EmailCodeConfirmation code);
>>>>>>> develop

    UserSpecificationBuilder role(RoleName role);
}