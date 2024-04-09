package com.kenzie.appserver.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = PasswordEncoderConfig.class)
public class PasswordEncoderConfigTest {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    void passwordEncoderBeanIsNotNull() {
        assertNotNull(passwordEncoder);
    }

    @Test
    void passwordEncoderStrength() {
        String encodedPassword = passwordEncoder.encode("password");
        assertTrue(passwordEncoder.matches("password", encodedPassword));
    }
}

