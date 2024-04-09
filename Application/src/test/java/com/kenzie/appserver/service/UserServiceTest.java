package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    LambdaServiceClient lambdaServiceClient;

    @InjectMocks
    UserService userService;

    @BeforeEach
    void initiate() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder, lambdaServiceClient);
    }

    @Test
    void createUser_Success() {
        // GIVEN
        User inputUser = new User("1", "someguy", "P@ssw0rd", "someguy@email.com", "household");
        UserRecord expectedUserRecord = new UserRecord("1", "someguy", passwordEncoder.encode("P@ssw0rd"), "someguy@email.com", "household");
        when(userRepository.save(any(UserRecord.class))).thenReturn(expectedUserRecord);
        when(passwordEncoder.encode(anyString())).thenReturn("EncodedP@ssw0rd");

        // WHEN
        UserRecord result = userService.createUser(inputUser);

        // THEN
        verify(userRepository).save(refEq(expectedUserRecord, "password")); // Ignore password field in refEq for simplicity
        assertEquals("someguy", result.getUsername());
    }


    @Test
        // Test for exception when user already exists
    void createUser_AlreadyExists() {
        // WHEN
        User userDto = new User(
                null,
                "someguy",
                "someguy@email.com",
                "P@ssw0rd",
                "household"
        );

        when(userRepository.findByUsername("someguy")).thenReturn(new UserRecord());

        // THEN
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto);
        });
    }

    @Test
    void getUserByIdTest() {
        String id = UUID.randomUUID().toString();
        UserRecord userRecord = new UserRecord();
        when(userRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(userRecord));

        UserRecord result = userService.getUserById(id);

        assertEquals(userRecord, result);
        verify(userRepository).findById(id);
    }


    @Test
    void updateUserTest() {
        UserRecord userRecord = new UserRecord();

        when(userRepository.save(userRecord)).thenReturn(userRecord);

        UserRecord result = userService.updateUser(userRecord);

        assertEquals(userRecord, result);
        verify(userRepository).save(userRecord);
    }

    @Test
    void getUserByIdNotFoundTest() {
        // GIVEN
        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN
        UserRecord result = userService.getUserById(id);

        // THEN
        assertNull(result);
        verify(userRepository).findById(id);
    }

    /** ADD MORE TESTS
    **/

}
