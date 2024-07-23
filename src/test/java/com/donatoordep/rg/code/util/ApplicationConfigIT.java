package com.donatoordep.rg.code.util;

import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.TaskRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public abstract class ApplicationConfigIT {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    protected EmailCodeConfirmationRepository emailCodeConfirmationRepository;

    @Autowired
    protected UserService userService;
}