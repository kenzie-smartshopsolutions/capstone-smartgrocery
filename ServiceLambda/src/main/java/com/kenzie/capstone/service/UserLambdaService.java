package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;

import javax.inject.Inject;

public class UserLambdaService {
    private UserDao userDao;

    @Inject
    public UserLambdaService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserData getUserData(String userId) {
        UserRecord userRecord = userDao.getUserRecord(userId);
        return userDao.convertToUserData(userRecord);
    }

    public UserData setUserData(UserData userData) {
        UserRecord userRecord = userDao.convertToUserRecord(userData);
        userRecord = userDao.storeUserRecord(userRecord);
        return userDao.convertToUserData(userRecord);
    }
}
