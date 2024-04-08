package com.kenzie.appserver.controller;

import com.kenzie.appserver.config.JwtTokenProvider;
import com.kenzie.appserver.controller.model.user.UserRequest;
import com.kenzie.appserver.controller.model.user.UserResponse;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("User")
public class UserController {

    private UserService userService;
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
  
    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    // Get user by ID
    @GetMapping("/register/userId/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        UserRecord userRecord = userService.getUserById(userId);

        if (userRecord != null) {
            User userDto = userService.convertToDto(userRecord);
            return ResponseEntity.ok(userDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Register a new user
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody User userDTO) {
        UserRecord createdUser = userService.createUser(userDTO);
        UserResponse responseDTO = userService.convertToUserResponse(createdUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    // Update an existing user
    @PutMapping("/register/userId/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable String userId, @RequestBody User userDto) {
        if (userService.getUserById(userId) != null) {
            UserRecord userRecord = userService.convertFromDto(userDto);
            userRecord.setUserId(userId);
            UserRecord updatedUser = userService.updateUser(userRecord);
            UserResponse responseDTO = userService.convertToUserResponse(updatedUser);
            //return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            return ResponseEntity.ok(responseDTO);
        } else {
           // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return ResponseEntity.notFound().build(); // Using ResponseEntity's convenience methods
        }
    }

    // Delete a user by ID
    @DeleteMapping("/register/userId/{userId}")
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

    // Login a user
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        // Handles the process of validating user credentials
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generates JWT token & responses
            String jwt = tokenProvider.generateToken(authentication);
            return ResponseEntity.ok().body("User logged in successfully with Token:" + jwt);

        // Manages account lockout due to multiple failed login attempts
        } catch (LockedException e) {
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body("Your account has been locked due to multiple failed login attempts. " +
                            "Please contact support to unlock your account.");
        } catch (BadCredentialsException e) {
            userService.incrementFailedLoginAttempts(userRequest.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        }
    }

    // Logout a user
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);

            // Add JWT Token to blacklist
            tokenProvider.blacklistToken(token);
        }
        return ResponseEntity.ok().build();
    }
}
