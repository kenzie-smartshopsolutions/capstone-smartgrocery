package com.kenzie.appserver.service;

import com.kenzie.appserver.config.SecurityConfig;
import com.kenzie.appserver.service.model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecurityConfigTest {

    @InjectMocks
    private SecurityConfig securityConfig;

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManagerBuilder auth;

    private UserDetails userDetailsMock;


    @Before
    public void setUp() {
        // Manually create mocks
        userService = Mockito.mock(UserService.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        auth = Mockito.mock(AuthenticationManagerBuilder.class);

        securityConfig = new SecurityConfig(userService, passwordEncoder);

        // Create test user
        User userMock = getTestUser();

        // Create UserDetails mock based on the user
        userDetailsMock = new org.springframework.security.core.userdetails.User(
                userMock.getUsername(),
                userMock.getPassword(),
                userMock.isAccountNonLocked(),
                true,
                true,
                true,
                Collections.emptyList());
    }


    private User getTestUser() {
        return new User (
                "105f1de6-03b7-4e5e-afbe-414f0a4301a7",
                "someguy",
                "P@ssw0rd",
                "someguy@email.com",
                "household"

        );
    }
    @Test
    public void shouldConfigureAuthenticationManagerBuilder() throws Exception {
        // GIVEN
        when(userService.loadUserByUsername("someguy")).thenReturn(userDetailsMock);

        // WHEN
        securityConfig.configure(auth);

        // THEN
        verify(auth).userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
