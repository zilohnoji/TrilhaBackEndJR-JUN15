package com.donatoordep.rg.code.controllers;

import com.donatoordep.rg.code.dtos.request.UserRequestAuthenticationDTO;
import com.donatoordep.rg.code.dtos.request.UserRequestRegisterDTO;
import com.donatoordep.rg.code.entities.EmailCodeConfirmation;
import com.donatoordep.rg.code.entities.User;
import com.donatoordep.rg.code.factory.EmailCodeConfirmationFactory;
import com.donatoordep.rg.code.factory.UserFactory;
import com.donatoordep.rg.code.util.ApplicationConfigIT;
import com.donatoordep.rg.code.util.TokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerIT extends ApplicationConfigIT {

    private User clientUser;
    private String clientToken;

    @BeforeEach
    void setup() {
        this.clientUser = UserFactory.createUser();
        this.clientToken = TokenUtil.generateJwtToken(this.clientUser);
    }

    @Test
    void registerShouldReturnUserResponseRegisterDTOWhenRequestValidData() throws Exception {
        UserRequestRegisterDTO request = new UserRequestRegisterDTO("user", "user@gmail.com", "12345678");

        ResultActions result = mockMvc.perform(post("/users").header("Authorization ", "Bearer " + this.clientToken)
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(request)));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.identifier").exists());
    }

    @Test
    void authenticationShouldReturnUserResponseAuthenticationDTOWhenRequestValidData() throws Exception {
        UserRequestAuthenticationDTO request = new UserRequestAuthenticationDTO("test@gmail.com", "12345678");

        ResultActions result = this.mockMvc.perform(post("/users/auth")
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json")
                .content(this.objectMapper.writeValueAsString(request)));

        result.andExpect(jsonPath("$.token").exists());
        result.andExpect(jsonPath("$.email").value(request.email()));
    }

    @Test
    void activeAccountShouldReturnOkWhenRequestValidToken() throws Exception {
        EmailCodeConfirmation emailCodeConfirmation = EmailCodeConfirmationFactory.persist(this.emailCodeConfirmationRepository, EmailCodeConfirmationFactory.createCode());
        User user = UserFactory.persist(this.userRepository, UserFactory.createUser(emailCodeConfirmation));

        ResultActions result = mockMvc.perform(post("/users/" + emailCodeConfirmation.getCode())
                .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("test@gmail.com")
    void getMeShouldReturnUserResponseGetProfileInfoDTOWhenRequestValidData() throws Exception {
        ResultActions result = mockMvc.perform(get("/users/me")
                .accept(MediaType.APPLICATION_JSON)
                .contentType("application/json"));

        result.andExpect(status().isOk());
    }
}