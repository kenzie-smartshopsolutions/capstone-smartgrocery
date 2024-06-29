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
            throw new RuntimeException("Error fetching user data", e);
        }
    }

    public UserData setUserData(UserData userData) {
        try {
            validateUserData(userData);
            String userId = userData.getUserId();
            UserRecord userRecord = userDao.setUserData(userId, userData);
            return userDao.convertToUserData(userRecord);
        } catch (Exception e) {
            log.error("Error setting user data, error: {}", e.getMessage());
            throw new RuntimeException("Error setting user data", e);
        }
    }

    public void deleteUserData(String userId) {
        try {
            userDao.deleteUserRecord(userId);
        } catch (Exception e) {
            log.error("Error deleting user data for ID: {}, error: {}", userId, e.getMessage());
            throw new RuntimeException("Error deleting user data", e);
        }
    }

    public UserData updateUserData(UserData userData) {
        try {
            validateUserData(userData);
            return userDao.updateUserData(userData);
        } catch (Exception e) {
            log.error("Error updating user data for ID: {}, error: {}", userData.getUserId(), e.getMessage());
            throw new RuntimeException("Error updating user data", e);
        }
    }

    private void validateUserData(UserData userData) {
        if (userData == null || userData.getUserId() == null || userData.getUserId().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (userData.getPassword() == null || userData.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }    }
}
