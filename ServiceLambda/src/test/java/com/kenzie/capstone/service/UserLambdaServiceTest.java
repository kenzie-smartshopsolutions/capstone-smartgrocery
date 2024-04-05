package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserLambdaServiceTest {

    private UserDao userDao;
    private UserLambdaService userLambdaService;

    @BeforeEach
    void setUp() {
        this.userDao = mock(UserDao.class);
        userLambdaService = new UserLambdaService(userDao);
    }

    @Test
    void getUserDataTest() {
        // Given
        String userId = "testUserId";
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setUsername("testUsername");
        when(userDao.getUserData(userId)).thenReturn(userRecord);

        // When
        UserData result = userLambdaService.getUserData(userId);

        // Then
        verify(userDao).getUserData(userId);
        assertEquals(userId, result.getUserId());
        assertEquals("testUsername", result.getUsername());
    }

    @Test
    void setUserDataTest() {
        // Given
        UserData userData = new UserData();
        userData.setUserId("testUserId");
        userData.setUsername("testUsername");
        ArgumentCaptor<UserData> userDataCaptor = ArgumentCaptor.forClass(UserData.class);
        when(userDao.setUserData(anyString(), any(UserData.class))).thenReturn(new UserRecord());

        // When
        userLambdaService.setUserData(userData);

        // Then
        verify(userDao).setUserData(eq(userData.getUserId()), userDataCaptor.capture());
        UserData capturedUserData = userDataCaptor.getValue();
        assertEquals("testUserId", capturedUserData.getUserId());
        assertEquals("testUsername", capturedUserData.getUsername());
    }

    // Additional tests for deleteUserData and updateUserData can be added here
}
