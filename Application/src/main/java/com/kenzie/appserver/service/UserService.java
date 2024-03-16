package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private BCryptPasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private LambdaServiceClient lambdaServiceClient;

    public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.lambdaServiceClient = lambdaServiceClient;
    }

    // Retrieve a user by their ID
    public UserRecord getUserById(String userId) {
        // User getting data from the lambda
        //UserData dataFromLambda = lambdaServiceClient.getUserData(userId);



        return userRepository.findById(userId).orElse(null);
    }

    // Create a new user
    public UserRecord createUser(UserRecord userRecord) {
        // User getting data from the lambda
        //UserData dataFromLambda = lambdaServiceClient.setUserData(userId);

        /*
        //????reference from ExampleService
         // User sending data to the local repository
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(dataFromLambda.getUserId());
        userRecord.setUserName(dataFromLambda.getData());
        userRepository.save(userRecord);

        User user = new User(dataFromLambda.getUserId(), userName);
        return user;
         */

        // ????? handle password hashing here before saving the user
        String encodedPassword = passwordEncoder.encode(userRecord.getPasswordHash());
        userRecord.setPasswordHash(encodedPassword);
        return userRepository.save(userRecord);
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
        UserRecord userRecord = userRepository.findByEmail(email);
        if (userRecord != null && passwordEncoder.matches(password, userRecord.getPasswordHash())) {
            return userRecord;
        } else {
            return null;
        }
    }
}

