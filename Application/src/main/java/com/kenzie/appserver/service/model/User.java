package com.kenzie.appserver.service.model;

public class User {

    /*userId (String)
username (String)
email (String)
passwordHash (String)
dateCreated**
householdName (String) - [in case of multiple household members sharing same pantry]**
*/
    private String userId;
    private final String username;
    private final String email;
    private final String password;
    private final String householdName;
    private boolean accountNonLocked;
    private int failedLoginAttempts;

    public User(String userId, String username, String email, String password, String householdName) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.householdName = householdName;
        this.accountNonLocked = true;
        this.failedLoginAttempts = 0;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getHouseholdName() {
        return householdName;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
