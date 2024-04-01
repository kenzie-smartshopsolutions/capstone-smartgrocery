package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
@DynamoDBTable(tableName = "User")
public class UserRecord implements UserDetails {
    private  String userId;
    private  String username;
    private  String email;
    private String password;
    private  String householdName;
    private boolean accountNonLocked;
    private int failedLoginAttempts;

    public UserRecord(String userId, String username, String password, String email, String householdName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.householdName = householdName;
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }
    // Added to track failed login attempts and if account is locked
    public UserRecord() {
        // Accounts start in a non-locked state
        this.accountNonLocked = true;

        // No failed attempts initially
        this.failedLoginAttempts = 0;
    }

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @DynamoDBAttribute(attributeName = "householdName")
    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
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

    /** Added to implement and adapt UserDetails interface from Spring Boot Security
     * Need to add logic (if applicable) or keep things straightforward and simple
     **/

    // returns role authorities granted to user
    @DynamoDBIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @DynamoDBIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @DynamoDBIgnore
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @DynamoDBIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @DynamoDBIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public int getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(int failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }
}
