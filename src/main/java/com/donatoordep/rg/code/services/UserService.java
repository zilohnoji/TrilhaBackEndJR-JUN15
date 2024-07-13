package com.donatoordep.rg.code.services;

import com.auth0.jwt.JWT;
import com.donatoordep.rg.code.configurations.security.JWTTokenUtil;
import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.exceptions.ONBEmailCodeConfirmationDoesNotExistsException;
import com.donatoordep.rg.code.mappers.dto.response.UserResponseDTOMapper;
import com.donatoordep.rg.code.mappers.entities.UserMapper;
import com.donatoordep.rg.code.repositories.EmailCodeConfirmationRepository;
import com.donatoordep.rg.code.repositories.UserRepository;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountArgs;
import com.donatoordep.rg.code.services.validations.user.chain.activeAccount.UserActiveAccountValidation;
import com.donatoordep.rg.code.services.validations.user.chain.authentication.UserAuthenticationArgs;
import com.donatoordep.rg.code.services.validations.user.chain.authentication.UserAuthenticationValidation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private List<UserAuthenticationValidation> authenticationValidations;

    @Autowired
    private List<UserActiveAccountValidation> activeAcountValidations;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService,
                       AuthenticationManager manager, JWTTokenUtil jwtTokenUtil, EmailCodeConfirmationRepository emailCodeConfirmationRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.manager = manager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.emailCodeConfirmationRepository = emailCodeConfirmationRepository;
    }

    public UserResponseRegisterDTO register(UserRequestRegisterDTO request) throws MessagingException, UnsupportedEncodingException {

        User entity = UserMapper.toEntity(request);

        entity.setCode(EmailCodeConfirmation.createCodeConfirmation(LocalDateTime.now().plusMinutes(30), 32));
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        emailService.sendCodeForEmail(entity.getCode(), request.getEmail());

        return UserResponseDTOMapper.toResponse(userRepository.save(entity));
    }

    public UserResponseAuthenticationDTO authentication(UserRequestAuthenticationDTO request) {

        authenticationValidations.forEach(validation -> {
            validation.validate(new UserAuthenticationArgs(request, userRepository));
        });

        Authentication authenticate = this.authenticate(request.getEmail(), request.getPassword());
        User entity = (User) authenticate.getPrincipal();
        String tokenJwt = jwtTokenUtil.generateToken(entity);
        return UserResponseAuthenticationDTO.ofAuthentication(entity.getEmail(), tokenJwt, JWT.decode(tokenJwt).getExpiresAt().toString());
    }

    public void activeAccount(String token) {

        activeAcountValidations.forEach(validation -> {
            validation.validate(new UserActiveAccountArgs(token, emailCodeConfirmationRepository));
        });

        User entity = userRepository.findByEmailCodeConfirmation(token).orElseThrow(ONBEmailCodeConfirmationDoesNotExistsException::new);
        userRepository.save(entity.activeAccount());
    }

    private Authentication authenticate(String username, String password) {
        return manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}