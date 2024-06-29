package com.kenzie.appserver.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.kenzie.appserver.config.UuidUtils;
import com.kenzie.appserver.controller.model.user.UserResponse;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;

import com.kenzie.capstone.service.model.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static com.kenzie.appserver.config.Role.USER;

@Service
public class UserService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final LambdaServiceClient lambdaServiceClient;
    private UuidUtils uuid;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.lambdaServiceClient = lambdaServiceClient;
    }

    // Create a new user
    public UserRecord createUser(UserData userData) {
        // Validate user details
        validateUserDetails(userData);

        // Generate userId only for new user creation
        String idCheck = userData.getUserId();
        if (idCheck == null || idCheck.trim().isEmpty() || !UuidUtils.isValidUUID(idCheck)) {
            String uniqueUserId = UUID.randomUUID().toString();

            // Set the generated userId on the DTO
            userData.setUserId(uniqueUserId);
        }

        // encode & save password
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));

        // Save user to local database
        UserRecord userRecord = convertToUserRecord(userData);
        userRepository.save(userRecord);


        // Convert UserRecord to UserData and "setUserData"
        UserData savedUser = convertToUserData(userRecord);
        lambdaServiceClient.setUserData(userData);

        return userRecord;
    }

    // Retrieve a user by their ID
    public UserRecord getUserById(String userId) {
        UserRecord userRecord = null;

        try {
            // Retrieve UserData from the lambda function
            UserData lambdaUserData = lambdaServiceClient.getUserData(userId);

            // Convert UserData to User
            User lambdaUser = new User(lambdaUserData.getUserId(),
                    lambdaUserData.getUsername(),
                    lambdaUserData.getPassword(),
                    lambdaUserData.getEmail(),
                    lambdaUserData.getHouseholdName());

            // Convert User to UserRecord
            userRecord = convertFromDto(lambdaUser);
        } catch (Exception e) {
            // Handle exceptions that occurred when retrieving from the Lambda function
            System.err.println("Unable to retrieve data from the Lambda function: " + e.getMessage());

            // Retrieve UserRecord from the local database as contingency
            userRecord = userRepository.findByUserId(userId);

            if (userRecord == null) {
                throw new IllegalArgumentException("User not found in databases.");
            }
        }
        return userRecord;
    }

    // Update an existing user
    public UserRecord updateUser(UserRecord userRecord) {
        // Check if the user exists
        Optional<UserRecord> existingUserRecord = userRepository.findById(userRecord.getUserId());
        if (!existingUserRecord.isPresent()) {
            throw new IllegalArgumentException("User not found with ID: " + userRecord.getUserId());
        }

        return userRepository.save(userRecord);
    }

    // Delete a user by their ID
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }

    /** Helper methods for conversions **/

    public UserData getUserByUsername(String username) {
        UserRecord userRecord = userRepository.findByUsername(username);
        if (userRecord != null) {
            return convertToUserData(userRecord);
        }
        return null;
    }

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
                userRecord.getPassword(),
                userRecord.getEmail(),
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
        userData.setRole(userRecord.getRole());

        return userData;
    }
    public UserResponse convertToUserResponse(UserRecord userRecord) {
        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(userRecord.getUserId());
        userResponse.setUsername(userRecord.getUsername());
        // Password is not set due to security reasons
        userResponse.setEmail(userRecord.getEmail());
        userResponse.setHouseholdName(userRecord.getHouseholdName());
        userResponse.setFailedLoginAttempts(userRecord.getFailedLoginAttempts());
        userResponse.setRole(userResponse.getRole());

        return userResponse;
    }

    private UserRecord convertToUserRecord(UserData userData) {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userData.getUserId());
        userRecord.setUsername(userData.getUsername());
        userRecord.setPassword(userData.getPassword());
        userRecord.setEmail(userData.getEmail());
        userRecord.setHouseholdName(userData.getHouseholdName());
        userRecord.setFailedLoginAttempts(userData.getFailedLoginAttempts());
        userRecord.setRole(userData.getRole());

        return userRecord;
    }
    // Increment failed login attempts for a user
    public void incrementFailedLoginAttempts(String username) {
        UserRecord user = userRepository.findByUsername(username);
        if (user != null) {
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);
            if (user.getFailedLoginAttempts() > 5) {
                user.setAccountNonLocked(false);
            }
            userRepository.save(user);
        }
    }

    // Unlock an account for a user
    public void resetAndUnlockAccount(String username) {
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
//        return user;
//        //Return UserDetails object required by Spring Security for authentication
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
    private void validateUserDetails(UserData userData) {
        // Check if username exists
        if (userRepository.findByUsername(userData.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists.");
        }
        // Check if email exists
        if (userRepository.findByEmail(userData.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }
        // Validate the password
        if (!isValidPassword(userData.getPassword())) {
            throw new IllegalArgumentException("Password does not meet security requirements.");
        }
    }
}
