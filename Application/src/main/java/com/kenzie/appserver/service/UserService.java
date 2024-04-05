package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.user.UserResponse;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LambdaServiceClient lambdaServiceClient;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    // Retrieve a user by their ID
    public UserRecord getUserById(String userId) {
        // User getting data from the lambda
        //UserData dataFromLambda = lambdaServiceClient.getUserData(userId);
        return userRepository.findById(userId).orElse(null);
    }

    // Create a new user
    public UserRecord createUser(User userDto) {
        // Validate user details
        validateUserDetails(userDto);

        // Generate userId only for new user creation
        if (userDto.getUserId() == null || userDto.getUserId().trim().isEmpty()) {
            String uniqueUserId = UUID.randomUUID().toString();

            // Set the generated userId on the DTO
            userDto.setUserId(uniqueUserId);
        }

        UserRecord userRecord = convertFromDto(userDto);

        // encode & save password
        userRecord.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserRecord savedUser = userRepository.save(userRecord);

        // Convert UserRecord to UserData and "setUserData"
        UserData userData = convertToUserData(savedUser);
        lambdaServiceClient.setUserData(userData);

        return savedUser;
    }

    /** Method to validate password against password policies
     * Password policy:
     * At least 8 characters,
     * one uppercase, one lowercase,
     * one digit, one special character
    **/
    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
    }

    // helper method that performs validation checks for registration
    private void validateUserDetails(User userDto) {
        // Check if username exists
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        // Check if email exists
        if (userRepository.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        // Validate the password
        if (!isValidPassword(userDto.getPassword())) {
            throw new IllegalArgumentException("Password does not meet security requirements.");
        }
    }

    /** Helper methods to convert DTOs **/
    // Convert DTO to UserRecord (Entity)
    public UserRecord convertFromDto(User userDto) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userDto.getUserId());
        userRecord.setUsername(userDto.getUsername());
        userRecord.setPassword(userDto.getPassword());
        userRecord.setEmail(userDto.getEmail());
        userRecord.setHouseholdName(userDto.getHouseholdName());
        return userRecord;
    }

    // Convert UserRecord (Entity) to DTO
    public User convertToDto(UserRecord userRecord) {
        return new User(
                userRecord.getUserId(),
                userRecord.getUsername(),
                userRecord.getEmail(),
                userRecord.getPassword(),
                userRecord.getHouseholdName());
    }

    // Convert UserRecord to UserData
    public UserData convertToUserData(UserRecord userRecord) {
        if (userRecord == null) {
            return null;
        }
        UserData userData = new UserData();
        userData.setUserId(userRecord.getUserId());
        userData.setUsername(userRecord.getUsername());
        userData.setPassword(userRecord.getPassword()); // Note: Consider security implications
        userData.setEmail(userRecord.getEmail());
        userData.setHouseholdName(userRecord.getHouseholdName());

        return userData;
    }
    public UserResponse convertToUserResponse(UserRecord userRecord) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRecord.getUserId());
        userResponse.setUsername(userRecord.getUsername());
        // Note: Password is not set due to security reasons
        userResponse.setEmail(userRecord.getEmail());
        userResponse.setHouseholdName(userRecord.getHouseholdName());
        userResponse.setFailedLoginAttempts(userRecord.getFailedLoginAttempts());
        return userResponse;
    }
    // Update an existing user
    public UserRecord updateUser(UserRecord userRecord) {
        return userRepository.save(userRecord);
    }


    // Delete a user by their ID
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    // Validate user credentials and perform login
    public UserRecord loginUser(String email, String password) {
        // ? validate the password and generate authentication
        // Return the user record if login is successful, otherwise return null


        // Retrieve the user record by email from the repository
        UserRecord userRecord = userRepository.findByEmail(email);

        // Check if the user exists and the provided password matches the stored hashed password
        if (userRecord != null && passwordEncoder.matches(password, userRecord.getPassword())) {
            return userRecord;
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
    }

    // Increment failed login attempts for a user
    public void incrementFailedLoginAttempts(String username) {
        UserRecord user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() >= 5) {
                user.setAccountNonLocked(false);
            }
            userRepository.save(user);
        }
    }

    // Reset failed login attempts for a user
    public void resetFailedLoginAttempts(String username) {
        UserRecord user = userRepository.findByUsername(username);
        if (user != null && user.isAccountNonLocked()) {
            user.setFailedLoginAttempts(0);
            userRepository.save(user);
        }
    }

    // Unlock an account for a user
    public void unlockAccount(String username) {
        UserRecord user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFailedLoginAttempts(0);
            user.setAccountNonLocked(true);
            userRepository.save(user);
        }
    }

    // // Load user details by username for Spring Security
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRecord user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        //Return UserDetails object required by Spring Security for authentication
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                // This parameter determines if the account is nonLocked
                user.isAccountNonLocked(),
                true,
                true,
                true,
                Collections.emptyList());
    }
}
