package com.donatoordep.rg.code.util;

import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.TaskRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.services.EmailService;
import com.donatoordep.rg.code.services.validations.task.update.TaskUpdateValidation;
import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountValidation;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationValidation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public abstract class ApplicationConfigTest {

    @Mock
    protected JavaMailSender javaMailSender;

    @Mock
    protected TaskRepository taskRepository;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected EmailCodeConfirmationRepository emailCodeConfirmationRepository;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @Mock
    protected EmailService emailService;

    @Mock
    protected AuthenticationManager manager;

    @Mock
    protected JWTTokenUtil jwtTokenUtil;

    protected List<UserAuthenticationValidation> authenticationValidations;

    protected List<UserActiveAccountValidation> activeAccountValidations;

    protected List<TaskUpdateValidation> updateValidations;
}