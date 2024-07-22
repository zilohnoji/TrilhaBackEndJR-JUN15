package com.donatoordep.rg.code.util;

import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public abstract class ApplicationConfigIT {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected EmailCodeConfirmationRepository emailCodeConfirmationRepository;

    @Autowired
    protected UserService userService;
}