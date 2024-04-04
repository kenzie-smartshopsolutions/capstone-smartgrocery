package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    LambdaServiceClient lambdaServiceClient;

    UserService userService;

    @BeforeEach
    void initiate() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, passwordEncoder, lambdaServiceClient);
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
        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());

        UserRecord result = userService.getUserById(id);

        assertNull(result);
        verify(userRepository).findById(id);
    }

    /** ADD MORE TESTS
    **/

}
