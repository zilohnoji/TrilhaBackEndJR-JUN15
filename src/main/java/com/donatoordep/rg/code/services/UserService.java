package com.donatoordep.rg.code.services;

import com.auth0.jwt.JWT;
import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.mappers.entities.UserMapper;
import com.donatoordep.rg.code.repositories.impl.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.impl.UserRepository;
import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountArgs;
import com.donatoordep.rg.code.services.validations.user.activeAccount.UserActiveAccountValidation;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationArgs;
import com.donatoordep.rg.code.services.validations.user.authentication.UserAuthenticationValidation;
import jakarta.mail.MessagingException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final AuthenticationManager manager;
    private final JWTTokenUtil jwtTokenUtil;
    private final EmailCodeConfirmationRepository emailCodeConfirmationRepository;

    private List<UserAuthenticationValidation> authenticationValidations;
    private List<UserActiveAccountValidation> activeAccountValidations;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService,
                       AuthenticationManager manager, JWTTokenUtil jwtTokenUtil, EmailCodeConfirmationRepository emailCodeConfirmationRepository,
                       List<UserAuthenticationValidation> authenticationValidations, List<UserActiveAccountValidation> activeAccountValidation) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.manager = manager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.activeAccountValidations = activeAccountValidation;
        this.authenticationValidations = authenticationValidations;
        this.emailCodeConfirmationRepository = emailCodeConfirmationRepository;
    }

    public UserResponseRegisterDTO register(UserRequestRegisterDTO request) throws MessagingException, UnsupportedEncodingException {
        User entity = UserMapper.toEntity(request);

        entity.setCode(EmailCodeConfirmation.createCodeConfirmation(LocalDateTime.now().plusMinutes(30), 32));
        entity.setPassword(passwordEncoder.encode(request.password()));

        emailService.sendCodeForEmail(entity.getCode(), request.email());

        return UserMapper.toResponseForRegisterDto(userRepository.save(entity));
    }

    public UserResponseAuthenticationDTO authentication(UserRequestAuthenticationDTO request) {
        authenticationValidations.forEach(validation -> {
            validation.validate(new UserAuthenticationArgs(request, userRepository));
        });

        Authentication authenticate = this.authenticate(request.email(), request.password());
        User entity = (User) authenticate.getPrincipal();
        String tokenJwt = jwtTokenUtil.generateToken(entity);

        return UserResponseAuthenticationDTO.ofAuthentication(entity.getEmail(), tokenJwt, JWT.decode(tokenJwt).getExpiresAt().toString());
    }

    public void activeAccount(String token) {
        activeAccountValidations.forEach(validation -> {
            validation.validate(new UserActiveAccountArgs(token, emailCodeConfirmationRepository));
        });

        User entity = userRepository.findByEmailCodeConfirmationOrThrowNotFound(token);
        entity.activeAccount();
        userRepository.save(entity);
    }

    public UserResponseGetProfileInfoDTO getUserProfile(User user) {
        return UserMapper.toResponseForGetAllDto(userRepository.findByIdOrThrowNotFound(user.getId()));
    }

    private Authentication authenticate(String username, String password) {
        return manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    public void setAuthenticationValidations(List<UserAuthenticationValidation> authenticationValidations) {
        this.authenticationValidations = authenticationValidations;
    }

    public void setActiveAccountValidations(List<UserActiveAccountValidation> activeAccountValidations) {
        this.activeAccountValidations = activeAccountValidations;
    }
}