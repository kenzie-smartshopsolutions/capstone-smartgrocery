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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
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
    public void createUser_Success() {
        // Arrange
        User input = new User("1", "someguy", "someguy@email.com","p@ssword","household");
        UserRecord output = new UserRecord("1", "someguy", "someguy@email.com","encodedPassword","household");

        when(userRepository.save(any(UserRecord.class))).thenReturn(output);

        // Act
        UserRecord savedUser = userService.createUser(input);

        // Assert
        assertEquals(savedUser.getUsername(), output.getUsername());
        assertEquals(savedUser.getEmail(), output.getEmail());
    }

    @Test
        // Test for exception when user already exists
    void testCreateUser_AlreadyExists() {
        // Arrange
        User userDto = new User(
                null,
                "someguy",
                "someguy@email.com",
                "P@ssw0rd",
                "household"
        );

        when(userRepository.findByUsername("someguy")).thenReturn(new UserRecord());

        // Act and Assert
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
        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(java.util.Optional.empty());

        UserRecord result = userService.getUserById(id);

        assertNull(result);
        verify(userRepository).findById(id);
    }

    /** ADD MORE TESTS
    **/

}
