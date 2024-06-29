package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "LoginLog")
public class LoginLog {

    private String logId;
    private String userId;
    private String username;
    private String date;

    @Id
    @DynamoDBHashKey(attributeName = "logId")
    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    @DynamoDBAttribute(attributeName = "userId")
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

    @DynamoDBAttribute(attributeName = "date")
    public String getLoginDate() {
        return date;
    }

    public void setLoginDate(String date) {
        this.date = date;
    }
}
