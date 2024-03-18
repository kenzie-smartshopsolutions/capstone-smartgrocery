package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.UserRequest;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        UserRecord userRecord = userService.getUserById(userId);

        if (userRecord != null) {
            User userDto = userService.convertToDto(userRecord);
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User userDTO) {
        UserRecord createdUser = userService.createUser(userDTO);
        User responseDTO = userService.convertToDto(createdUser);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    // Update an existing user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User userDto) {
        if (userService.getUserById(userId) != null) {
            UserRecord userRecord = userService.convertFromDto(userDto);
            userRecord.setUserId(userId);
            UserRecord updatedUser = userService.updateUser(userRecord);
            User responseDTO = userService.convertToDto(updatedUser);
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // TODO: Implement JWT token creation here and include it in the response
            return ResponseEntity.ok().body("User logged in successfully.");
        } catch (LockedException e) {
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body("Your account has been locked due to multiple failed login attempts. Please contact support to unlock your account.");
        } catch (BadCredentialsException e) {
            userService.incrementFailedLoginAttempts(userRequest.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        }
    }
}