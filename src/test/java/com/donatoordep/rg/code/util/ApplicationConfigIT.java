package com.donatoordep.rg.code.util;

import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public abstract class ApplicationConfigIT {

    @Autowired
    protected EmailCodeConfirmationRepository emailCodeConfirmationRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;
}