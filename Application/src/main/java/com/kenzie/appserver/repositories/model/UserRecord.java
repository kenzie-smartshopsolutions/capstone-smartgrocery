package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
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

    // Added to track failed login attempts and if account is locked
    public UserRecord() {
        // Accounts start in a non-locked state
        this.accountNonLocked = true;

        // No failed attempts initially
        this.failedLoginAttempts = 0;
    }

    @DynamoDBHashKey(attributeName = "UserId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    @DynamoDBAttribute(attributeName = "Username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @DynamoDBAttribute(attributeName = "Email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @DynamoDBAttribute(attributeName = "Password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordHash) {
        this.password = password;
    }
    @DynamoDBAttribute(attributeName = "HouseholdName")
    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserRecord userRecord = (UserRecord) o;
        return Objects.equals(userId, userRecord.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    /** Added to implement and adapt UserDetails interface from Spring Boot Security
     * Need to add logic (if applicable) or keep things straightforward and simple
     **/

    // returns role authorities granted to user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //returns authorize
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

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
