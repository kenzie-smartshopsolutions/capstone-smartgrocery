package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

public class UserLambdaService {
    private UserDao userDao;
    private static final Logger log = LoggerFactory.getLogger(UserLambdaService.class);
    @Inject
    public UserLambdaService(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserData getUserData(String userId) {
        try {
            UserRecord userRecord = userDao.getUserData(userId);

            // Handle user not found scenario
            if (userRecord == null) {
                log.error("User not found with ID: {}", userId);
                throw new RuntimeException("User not found");
            }
            return userDao.convertToUserData(userRecord);
        } catch (Exception e) {
            log.error("Error fetching user data for ID: {}, error: {}", userId, e.getMessage());
            throw e;
        }
    }

    public UserData setUserData(UserData userData) {
        String userId = userData.getUserId();
        UserRecord userRecord = userDao.setUserData(userId, userData);
        return userDao.convertToUserData(userRecord);
    }

    public void deleteUserData(String userId) {
        userDao.deleteUserRecord(userId);
    }

    public UserData updateUserData(UserData userData) {
        try {
            UserData updatedUserData = userDao.updateUserData(userData);
            return updatedUserData;
        } catch (Exception e) {
            log.error("Error updating user data for ID: {}, error: {}", userData.getUserId(), e.getMessage());
            throw e;
        }
    }
}
