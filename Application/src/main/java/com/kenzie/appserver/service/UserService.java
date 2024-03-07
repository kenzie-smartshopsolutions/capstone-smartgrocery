package com.kenzie.appserver.service;

import com.amazonaws.services.ec2.model.UserData;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.service.model.User;
import com.kenzie.capstone.service.client.LambdaServiceClient;


public class UserService {
    private UserRepository userRepository;
    private LambdaServiceClient lambdaServiceClient;

    public UserService(UserRepository userRepository, LambdaServiceClient lambdaServiceClient) {
        this.userRepository = userRepository;
        this.lambdaServiceClient = lambdaServiceClient;
    }
}

