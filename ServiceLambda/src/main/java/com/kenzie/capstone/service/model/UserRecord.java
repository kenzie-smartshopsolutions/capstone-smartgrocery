package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "User")
public class UserRecord {

    private String userId;
    private String username;
    private String password;
    private String email;
    private String householdName;
    private boolean accountNonLocked;
    private int failedLoginAttempts;
    @JsonIgnore
    private String role;

    public UserRecord(String userId,
                      String username,
                      String password,
                      String email,
                      String householdName,
                      String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.householdName = householdName;
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
        this.role = role;
    }

    public UserRecord() {
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }


    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "userIdIndex", attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return username;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    @DynamoDBAttribute(attributeName = "householdName")
    public String getHouseholdName() {
        return householdName;
    }

    @DynamoDBAttribute(attributeName = "accountNonLocked")
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @DynamoDBAttribute(attributeName = "failedLoginAttempts")
    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRecord that = (UserRecord) o;
        return accountNonLocked == that.accountNonLocked &&
                failedLoginAttempts == that.failedLoginAttempts &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email) &&
                Objects.equals(householdName, that.householdName) &&
                Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, username, householdName, accountNonLocked, failedLoginAttempts, role);
    }
}
