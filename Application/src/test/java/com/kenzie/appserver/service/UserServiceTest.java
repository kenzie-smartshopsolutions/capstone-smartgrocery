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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void createUser_InvalidPassword() {
        // GIVEN
        User inputUser = new User(null, "newUser", "weak", "newUser@email.com", "household");

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(inputUser), "Password does not meet security requirements.");
    }

    // Test for exception when user already exists
    @Test
    void createUser_AlreadyExists() {
        // GIVEN
        User userDto = new User(
                null,
                "someguy",
                "someguy@email.com",
                "P@ssw0rd",
                "household"
        );

        // WHEN
        when(userRepository.findByUsername("someguy")).thenReturn(new UserRecord());

        // THEN
        assertThrows(IllegalArgumentException.class, () -> {
            userService.createUser(userDto);
        });
    }

    @Test
    void getUserById_Success() {
        // GIVEN
        String userId = UUID.randomUUID().toString(); // Or a specific userId if needed.
        UserRecord expectedUserRecord = new UserRecord(userId, "existingUser", "EncodedP@ssw0rd", "user@example.com", "household");

        // Mock the UserRepository to return the expected UserRecord when findById is called with the userId
        when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUserRecord));

        // WHEN
        // Call the method under test
        UserRecord result = userService.getUserById(userId);

        // THEN
        // Validate that the result is not null and matches the expected UserRecord
        assertNotNull(result, "The result should not be null.");
        assertEquals(expectedUserRecord.getUserId(), result.getUserId(), "The userId should match the expected value.");

        // Verify that findById was called on the userRepository with the correct userId
        verify(userRepository).findById(userId);
    }



    @Test
    void getUserById_notFoundTest() {
        // GIVEN
        String id = UUID.randomUUID().toString();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(IllegalArgumentException.class, () -> userService.getUserById(id), "User not found in databases.");
    }

    @Test
    void updateUserTest() {
        // GIVEN
        String existingId = UUID.randomUUID().toString();
        UserRecord userRecord = new UserRecord(existingId, "existingUser", "EncodedP@ssw0rd", "user@example.com", "household");
        when(userRepository.findById(existingId)).thenReturn(Optional.of(userRecord));
        when(userRepository.save(userRecord)).thenReturn(userRecord);

        // WHEN
        UserRecord result = userService.updateUser(userRecord);

        // THEN
        assertEquals(userRecord, result);
        verify(userRepository).findById(existingId);
        verify(userRepository).save(userRecord);
    }

    @Test
    void updateUser_NotFound() {
        // GIVEN
        UserRecord userRecord = new UserRecord("nonexistentId", "nonUser", "EncodedP@ssw0rd", "nonUser@email.com", "household");
        when(userRepository.findById("nonexistentId")).thenReturn(Optional.empty());

        // THEN
        assertThrows(IllegalArgumentException.class, () -> userService.updateUser(userRecord), "User not found in databases.");
    }



    @Test
    void deleteUser_Success() {
        // GIVEN
        String userId = UUID.randomUUID().toString();

        // WHEN
        userService.deleteUser(userId);

        // THEN
        verify(userRepository).deleteById(userId);
    }


}
