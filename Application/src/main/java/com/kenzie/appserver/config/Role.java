package com.kenzie.appserver.config;

public enum Role {
    ADMIN,
    USER;

    @Override
    public String toString() {
        return this.name();
    }

    public static Role fromString(String role) {
        try {
            Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
        return Role.valueOf(role.toUpperCase());
    }
}
