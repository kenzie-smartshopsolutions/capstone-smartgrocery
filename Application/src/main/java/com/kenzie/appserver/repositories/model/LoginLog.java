package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "LoginLog")
public class LoginLog {


    private String userId;

    private String username;

    private LocalDateTime loginTime;

    @Id
    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userIdIndex", attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "userName")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBRangeKey(attributeName = "loginTime")
    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}
