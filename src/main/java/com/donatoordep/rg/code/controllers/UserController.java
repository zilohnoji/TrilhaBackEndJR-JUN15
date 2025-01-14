package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseAuthenticationDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseGetProfileInfoDTO;
import com.donatoordep.rg.code.dtos.response.UserResponseRegisterDTO;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
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
    @Operation(description = "Cadastrar um novo usuário", method = "POST")
    @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso")
    public ResponseEntity<UserResponseRegisterDTO> register(@RequestBody @Valid UserRequestRegisterDTO request)
            throws MessagingException, UnsupportedEncodingException {
        UserResponseRegisterDTO response = service.register(request);

        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getIdentifier()).toUri()).body(response);
    }

    @PostMapping(path = "/auth")
    @Operation(description = "Autenticar usuário", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário autenticado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro na entrada de dados")
    })
    public ResponseEntity<UserResponseAuthenticationDTO> authentication(@RequestBody @Valid UserRequestAuthenticationDTO request) {
        return ResponseEntity.ok().body(service.authentication(request));
    }

    @PostMapping(path = "/{token}")
    @Operation(description = "Ativar conta de usuário", method = "POST")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ativação efetuada com sucesso"),
    })
    public ResponseEntity<Void> activeAccount(@PathVariable String token) {
        service.activeAccount(token);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/me")
    @Operation(description = "Pegar dados do perfil", method = "GET")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autenticado")
    })
    public ResponseEntity<UserResponseGetProfileInfoDTO> getMe(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok().body(service.getUserProfile(user));
    }
}