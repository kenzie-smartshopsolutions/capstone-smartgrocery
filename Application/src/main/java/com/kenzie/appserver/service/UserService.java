package com.kenzie.appserver.service;

import com.amazonaws.services.ec2.model.UserData;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.ExampleData;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;
    private LambdaServiceClient lambdaServiceClient;

    public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
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
        return null;
    }
}

