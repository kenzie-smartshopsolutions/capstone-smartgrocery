package com.kenzie.appserver.service.model;

public class User {

    /*userId (String)
username (String)
email (String)
passwordHash (String)
dateCreated**
householdName (String) - [in case of multiple household members sharing same pantry]**
*/
    private final String userId;
    private final String userName;
    private final String email;
    private final String passwordHash;
    private final String householdName;

    public User(String userId, String userName, String email, String passwordHash, String householdName) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.householdName = householdName;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getHouseholdName() {
        return householdName;
    }
}
