package com.kenzie.capstone.service;

import com.kenzie.capstone.service.dao.UserDao;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.UserRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserLambdaServiceTest {

    private UserDao userDao;
    private UserLambdaService userLambdaService;

    @BeforeAll
    void setup() {
        this.userDao = mock(UserDao.class);
        this.userLambdaService = new UserLambdaService(userDao);
    }

    @Test
    void setUserDataTest() {
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<UserData> userDataCaptor = ArgumentCaptor.forClass(UserData.class);

        // GIVEN
        UserData userData = new UserData();
        userData.setUserId("someUserId");
        userData.setUsername("someUsername");
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userData.getUserId());
        userRecord.setUsername(userData.getUsername());
        when(userDao.setUserData(anyString(), any(UserData.class))).thenReturn(userRecord);

        // WHEN
        UserData response = userLambdaService.setUserData(userData);

        // THEN
        verify(userDao, times(1)).setUserData(userIdCaptor.capture(), userDataCaptor.capture());

        assertNotNull(userIdCaptor.getValue(), "An ID is captured");
        assertEquals(userData.getUsername(), userDataCaptor.getValue().getUsername(), "The username is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(userIdCaptor.getValue(), response.getUserId(), "The response ID should match");
        assertEquals(userData.getUsername(), response.getUsername(), "The response username should match");
    }

    @Test
    void getUserDataTest() {
        ArgumentCaptor<String> userIdCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String userId = "fakeUserId";
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setUsername("someUsername");
        when(userDao.getUserData(userId)).thenReturn(userRecord);

        // WHEN
        UserData response = userLambdaService.getUserData(userId);

        // THEN
        verify(userDao, times(1)).getUserData(userIdCaptor.capture());

        assertEquals(userId, userIdCaptor.getValue(), "The correct ID is used");

        assertNotNull(response, "A response is returned");
        assertEquals(userId, response.getUserId(), "The response ID should match");
        assertEquals(userRecord.getUsername(), response.getUsername(), "The response username should match");
    }

    // TODO Add test methods for deleteUserData and updateUserData
}

