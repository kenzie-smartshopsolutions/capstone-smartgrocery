package com.kenzie.appserver.controller;

import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserRecord> getUserById(@PathVariable String userId) {
        UserRecord userRecord = userService.getUserById(userId);

        if (userRecord != null) {
            return new ResponseEntity<>(userRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserRecord> registerUser(@RequestBody UserRecord userRecord) {
        UserRecord createdUser = userService.createUser(userRecord);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{userId}")
    public ResponseEntity<UserRecord> updateUser(@PathVariable String userId, @RequestBody UserRecord userRecord) {
        if (userService.getUserById(userId) != null) {
            userRecord.setUserId(userId);
            UserRecord updatedUser = userService.updateUser(userRecord);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (userService.getUserById(userId) != null) {
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Log in a user
    @PostMapping("/login")
    public ResponseEntity<UserRecord> loginUser(@RequestParam String email, @RequestParam String password) {
        UserRecord userRecord = userService.loginUser(email, password);
        if (userRecord != null) {
            return new ResponseEntity<>(userRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}