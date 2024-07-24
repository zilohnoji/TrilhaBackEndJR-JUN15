package com.donatoordep.rg.code.util;

import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.services.EmailService;
import com.donatoordep.rg.code.services.UserService;
import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountValidation;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.ExpiredTokenValidation;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.TokenDoesNotExistsValidation;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationValidation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
public abstract class ApplicationConfigTest {

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

    @Mock
    protected ExpiredTokenValidation expiredTokenValidation;

    @Mock
    protected TokenDoesNotExistsValidation tokenDoesNotExistsValidation;

    @Mock
    protected List<UserAuthenticationValidation> authenticationValidations;

    @Mock
    protected List<UserActiveAccountValidation> activeAccountValidations;

    @InjectMocks
    protected UserService userService;
}