package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kenzie.capstone.service.model.user.Role;


public class UserData {
    private String userId;
    private String username;
    private String password;
    private String email;
    @JsonIgnore
    private String householdName;
    @JsonIgnore
    private boolean accountNonLocked;
    @JsonIgnore
    private int failedLoginAttempts;
    @JsonIgnore
    private Role role;

    public UserData(String userId,
                    String username,
                    String password,
                    String email,
                    String householdName,
                    Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.householdName = householdName;
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
        this.role = role;
    }
    public UserData() {
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHouseholdName() {
        return householdName;
    }

    public void setHouseholdName(String householdName) {
        this.householdName = householdName;
    }

    public boolean isAccountNonLocked() {
        return accountNonLocked;
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

    public com.kenzie.appserver.config.Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
