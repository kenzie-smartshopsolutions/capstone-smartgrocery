package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;
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
//    public UserRecord createUser(User userDto) {
//        UserRecord userRecord = convertFromDto(userDto);
//
//        // Check if userId is not provided and generate a new one
//        if (userRecord.getUserId() == null || userRecord.getUserId().trim().isEmpty()) {
//            userRecord.setUserId(UUID.randomUUID().toString());
//        }
//
//        userRecord.setPassword(passwordEncoder.encode(userDto.getPassword()));
//
//        UserRecord savedUser = userRepository.save(userRecord);
//
//        //// check this shit -need to fix "data"
//        lambdaServiceClient.setUserData(String.valueOf(savedUser));
//
//        return savedUser;
//    }
    public UserRecord createUser(User userDto) {
        // Check if userId is not provided in the DTO and generate a new one
        if (userDto.getUserId() == null || userDto.getUserId().trim().isEmpty()) {
            String generatedUserId = UUID.randomUUID().toString();
            System.out.println("Generated userId: " + generatedUserId); // Logging for debugging
            userDto.setUserId(generatedUserId);
        }

        UserRecord userRecord = convertFromDto(userDto);

        userRecord.setPassword(passwordEncoder
                .encode(userDto.getPassword()));

        UserRecord savedUser = userRepository.save(userRecord);

        //// check this shit -need to fix "data"
        lambdaServiceClient.setUserData(String.valueOf(savedUser));

        return savedUser;
    }

    // Method to validate password against password policies
    private boolean isValidPassword(String password) {
        // Implement your password policy
        // Example policy: At least 8 characters, one uppercase, one lowercase, one digit, one special character
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        return password.matches(passwordRegex);
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
                Collections.emptyList());
    }
}

