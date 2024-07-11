package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.mappers.dto.response.UserResponseDTOMapper;
import com.donatoordep.rg.code.mappers.entities.UserMapper;
import com.donatoordep.rg.code.repositories.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public UserResponseRegisterDTO register(UserRequestRegisterDTO request) throws MessagingException, UnsupportedEncodingException {

        User entity = UserMapper.toEntity(request);

        entity.setCode(EmailService.generateEmailCodeConfirmation());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));

        emailService.sendCodeForEmail(entity.getCode(), request.getEmail());

        return UserResponseDTOMapper.toResponse(userRepository.save(entity));
    }
}