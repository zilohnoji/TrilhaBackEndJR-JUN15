package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.Task;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBAccountDoesNotActiveException;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationExpiredException;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.TaskFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.ExpiredTokenValidation;
import com.donatoordep.rg.code.services.validations.user.activeAccount.validations.TokenDoesNotExistsValidation;
import com.donatoordep.rg.code.services.validations.user.authentication.validations.AccountDoesNotActiveValidation;
import com.donatoordep.rg.code.util.ApplicationConfigTest;
import com.donatoordep.rg.code.util.TokenUtil;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@DisplayName("UserService - Service Class Test")
class UserServiceTest extends ApplicationConfigTest {

    @InjectMocks
    protected UserService userService;

    User entity;
    Task task;
    EmailCodeConfirmation code;
    EmailCodeConfirmation expiredCode;

    @BeforeEach
    void setup() {
        this.userService.setActiveAccountValidations(
                Arrays.asList(
                        new ExpiredTokenValidation(),
                        new TokenDoesNotExistsValidation()
                ));

        this.userService.setAuthenticationValidations(
                List.of(new AccountDoesNotActiveValidation())
        );

        code = EmailCodeConfirmationFactory.createCode();
        entity = UserFactory.createUser(code);
        expiredCode = EmailCodeConfirmationFactory.createCode(LocalDateTime.now().minusDays(10));
        task = TaskFactory.createTask(entity);

        this.entity.addTask(task);
    }

    @Test
    void registerShouldReturnUserResponseRegisterDTOWhenRequestValidData() throws MessagingException, UnsupportedEncodingException {
        Mockito.when(this.passwordEncoder.encode("12345678")).thenReturn("$2a$12$koPD.5mB3eCDsxdDN/gTJOZde/uE3wcP0vkYQrZhz6YE47tzZ2wYe");
        Mockito.when(this.userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(entity);

        UserRequestRegisterDTO request = new UserRequestRegisterDTO(entity.getName(), entity.getEmail(), "12345678");
        UserResponseRegisterDTO response = this.userService.register(request);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertNotNull(response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", entity.getId(), response.getIdentifier())
        );

        Assertions.assertEquals(request.email(), response.getEmail(),
                () -> String.format("Expected email '%s', but return '%s'", entity.getEmail(), response.getEmail())
        );
    }

    @Test
    void getUserProfileShouldReturnUserResponseGetProfileInfoDTOWhenAuthenticated() {
        Mockito.when(this.userRepository.findByIdOrThrowNotFound(entity.getId())).thenReturn(entity);
        UserResponseGetProfileInfoDTO response = this.userService.getUserProfile(entity);

        Assertions.assertNotNull(response, "Expected a object, but return null");

        Assertions.assertEquals(entity.getId(), response.getIdentifier(),
                () -> String.format("Expected id '%s', but return '%s'", entity.getId(), response.getIdentifier())
        );

        Assertions.assertEquals(1, response.getTasks().size(),
                () -> String.format("Expected size 1, but return '%s'", response.getTasks().size())
        );
    }

    @Test
    void authenticationShouldReturnUserResponseAuthenticationDTOWhenRequestValidData() {
        Authentication authentication = Mockito.mock(Authentication.class);
        entity.activeAccount();

        Mockito.when(this.userRepository.findByEmailOrThrowNotFound(entity.getEmail())).thenReturn(entity);
        Mockito.when(this.manager.authenticate(ArgumentMatchers.any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(entity);
        Mockito.when(this.jwtTokenUtil.generateToken(entity)).thenReturn(TokenUtil.generateJwtToken(entity));

        UserRequestAuthenticationDTO request = new UserRequestAuthenticationDTO(entity.getEmail(), "12345678");
        UserResponseAuthenticationDTO response = this.userService.authentication(request);

        Assertions.assertNotNull(response, "Expected a object, but return null");
        Assertions.assertNotNull(response.getToken(), "Expected a token, but return null");
        Assertions.assertEquals(entity.getEmail(), response.getEmail(),
                () -> String.format("Expected email '%s', but return '%s'", entity.getEmail(), response.getEmail())
        );
    }

    @Test
    void activeAccountShouldActiveUserWhenTokenIsValid() {
        Mockito.when(this.userRepository.findByEmailCodeConfirmationOrThrowNotFound(entity.getCode().getCode())).thenReturn(entity);
        Mockito.when(this.emailCodeConfirmationRepository.findByTokenOrThrowNotExists(code.getCode())).thenReturn(code);
        Mockito.when(this.userRepository.save(entity)).thenReturn(entity);

        Assertions.assertDoesNotThrow(() -> {
            this.userService.activeAccount(entity.getCode().getCode());
        }, "Expected nothing exception, but throw exception");
    }

    @Nested
    @DisplayName("Service Class Fail Test")
    class UserServiceFailTest {
        @Test
        void activeAccountShouldThrowEmailCodeConfirmationDoesNotExistsExceptionWhenTokenNotExists() {
            Mockito.when(emailCodeConfirmationRepository.findByTokenOrThrowNotExists("")).thenThrow(ONBEmailCodeConfirmationDoesNotExistsException.class);

            Assertions.assertThrows(ONBEmailCodeConfirmationDoesNotExistsException.class, () -> {
                userService.activeAccount("");
            });
        }

        @Test
        void authenticationShouldThrowONBAccountDoesNotActiveExceptionWhenAccountNotActive() {
            Mockito.when(userRepository.findByEmailOrThrowNotFound(entity.getEmail())).thenReturn(entity);
            UserRequestAuthenticationDTO request = new UserRequestAuthenticationDTO(entity.getEmail(), "12345678");

            Assertions.assertThrows(ONBAccountDoesNotActiveException.class, () -> {
                userService.authentication(request);
            });
        }

        @Test
        void activeAccountShouldThrowEmailCodeConfirmationExpiredExceptionWhenTokenHasExpired() {
            Mockito.when(emailCodeConfirmationRepository.findByTokenOrThrowNotExists(expiredCode.getCode())).thenReturn(expiredCode);

            Assertions.assertThrows(ONBEmailCodeConfirmationExpiredException.class, () -> {
                userService.activeAccount(expiredCode.getCode());
            });
        }
    }
}