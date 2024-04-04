package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;

import javax.inject.Inject;
import java.util.UUID;

public class UserLambdaService {
    private UserDao userDao;

    @Inject
    public UserLambdaService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserData getUserData(String userId) {
        UserRecord userRecord = userDao.getUserData(userId);
        return userDao.convertToUserData(userRecord);
    }

    public UserData setUserData(UserData userData) {
        String userId = UUID.randomUUID().toString();
        UserRecord userRecord = userDao.setUserData(userId, userData);

        return userDao.convertToUserData(userRecord);
    }
}
