package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "User")
public class UserRecord {

    private String userId;
    private String email;
    private String username;
    private String passwordHash;

    private String householdName;
    private boolean accountNonLocked;
    private int failedLoginAttempts;

    public UserRecord(String userId, String username, String passwordHash,String email, String householdName) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.email = email;
        this.householdName = householdName;
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }

    public UserRecord() {
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }

    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    @DynamoDBAttribute(attributeName = "Username")
    public String getUsername() {
        return username;
    }

    @DynamoDBAttribute(attributeName = "HouseholdName")
    public String getHouseholdName() {
        return householdName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecord that = (UserRecord) o;
        return accountNonLocked == that.accountNonLocked && failedLoginAttempts == that.failedLoginAttempts &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(email, that.email) &&
                Objects.equals(username, that.username) &&
                Objects.equals(householdName, that.householdName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, username, householdName, accountNonLocked, failedLoginAttempts);
    }


}