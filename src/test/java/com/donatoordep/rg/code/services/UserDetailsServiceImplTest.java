package com.donatoordep.rg.code.services;

import com.donatoordep.rg.code.util.ApplicationConfigTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;

@DisplayName("UserDetailsServiceImpl - Service Class Test")
class UserDetailsServiceImplTest extends ApplicationConfigTest {

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameShouldReturnUserDetailsWhenUsernameHasExists() {
        Mockito.when(this.userRepository.findByEmailForLoadUserDetails(ArgumentMatchers.anyString())).thenReturn(Mockito.mock(UserDetails.class));

        UserDetails response = this.userDetailsService.loadUserByUsername("user@gmail.com");

        Assertions.assertNotNull(response, "Expected a object, but return null");
    }
}