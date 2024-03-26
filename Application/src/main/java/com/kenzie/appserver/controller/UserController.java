package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.user.UserRequest;
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
@RequestMapping("User")
public class UserController {


    private UserService userService;


    private AuthenticationManager authenticationManager;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // Get user by ID
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        UserRecord userRecord = userService.getUserById(userId);

        if (userRecord != null) {
            User userDto = userService.convertToDto(userRecord);
            return ResponseEntity.ok(userDto); // Simplified response entity creation
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User userDTO) {
        UserRecord createdUser = userService.createUser(userDTO);
        User responseDTO = userService.convertToDto(createdUser);
       // return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO); // Simplified response entity creation
    }

    // Update an existing user
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable String userId, @RequestBody User userDto) {
        if (userService.getUserById(userId) != null) {
            UserRecord userRecord = userService.convertFromDto(userDto);
            userRecord.setUserId(userId);
            UserRecord updatedUser = userService.updateUser(userRecord);
            User responseDTO = userService.convertToDto(updatedUser);
            //return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            return ResponseEntity.ok(responseDTO); // Simplified response entity creation
        } else {
           // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build(); // Using ResponseEntity's convenience methods
        }
    }

    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        if (userService.getUserById(userId) != null) {
            userService.deleteUser(userId);
            //return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return ResponseEntity.noContent().build(); // Using ResponseEntity's convenience methods
        } else {
           // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build(); // Using ResponseEntity's convenience methods
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