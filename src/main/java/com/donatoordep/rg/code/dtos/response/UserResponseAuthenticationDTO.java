package com.donatoordep.rg.code.dtos.response;

import com.donatoordep.rg.code.builders.dtos.response.UserResponseAuthenticationDTOSpecificationBuilder;

public class UserResponseAuthenticationDTO {

    private String email;
    private String token;
    private String expiredAt;

    private UserResponseAuthenticationDTO() {
    }

    public static class UserResponseAuthenticationDTOBuilder implements UserResponseAuthenticationDTOSpecificationBuilder {

        private UserResponseAuthenticationDTO dto;

        private UserResponseAuthenticationDTOBuilder() {
            this.reset();
        }

        public static UserResponseAuthenticationDTOBuilder builder() {
            return new UserResponseAuthenticationDTOBuilder();
        }

        @Override
        public UserResponseAuthenticationDTO build() {
            return this.dto;
        }

        @Override
        public UserResponseAuthenticationDTOSpecificationBuilder email(String email) {
            this.dto.setEmail(email);
            return this;
        }

        @Override
        public UserResponseAuthenticationDTOSpecificationBuilder token(String token) {
            this.dto.setToken(token);
            return this;
        }

        @Override
        public UserResponseAuthenticationDTOSpecificationBuilder expiredAt(String expiredAt) {
            this.dto.setExpiredAt(expiredAt);
            return this;
        }

        @Override
        public void reset() {
            this.dto = new UserResponseAuthenticationDTO();
        }
    }

    public static UserResponseAuthenticationDTO ofAuthentication(String email, String token, String expiredAt) {
        return UserResponseAuthenticationDTO.UserResponseAuthenticationDTOBuilder.builder()
                .email(email)
                .token(token)
                .expiredAt(expiredAt)
                .build();
    }

    private void setEmail(String email) {
        this.email = email;
    }

    private void setToken(String token) {
        this.token = token;
    }

    private void setExpiredAt(String expiredAt) {
        this.expiredAt = expiredAt;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public String getExpiredAt() {
        return expiredAt;
    }
}