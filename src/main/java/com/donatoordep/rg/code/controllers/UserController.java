package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserResponseRegisterDTO> register(@RequestBody @Valid UserRequestRegisterDTO request)
            throws MessagingException, UnsupportedEncodingException {
        UserResponseRegisterDTO response = service.register(request);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getIdentifier()).toUri()).body(response);
    }

    @PostMapping(path = "/auth")
    public ResponseEntity<UserResponseAuthenticationDTO> authentication(@RequestBody @Valid UserRequestAuthenticationDTO request){
        return ResponseEntity.ok().body(service.authentication(request));
    }
}