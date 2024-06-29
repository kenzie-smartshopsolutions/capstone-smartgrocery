package com.kenzie.appserver.controller;

import com.kenzie.appserver.config.auth.JwtTokenProvider;
import com.kenzie.appserver.controller.model.user.UserRequest;
import com.kenzie.appserver.controller.model.user.UserResponse;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.service.LoginService;
import com.kenzie.capstone.service.model.UserData;
import com.kenzie.capstone.service.model.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
@RequestMapping("User")
public class UserController {
    private final LoginService loginService;
    private UserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;
  
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
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
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest request) {
        UserData newUser = new UserData();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword());
        newUser.setEmail(request.getEmail());
        newUser.setHouseholdName(request.getHouseholdName());

        // Set default values for userId, accountNonLocked, and failedLoginAttempts
        newUser.setUserId(UUID.randomUUID().toString());
        newUser.setAccountNonLocked(true);
        newUser.setFailedLoginAttempts(0);
        newUser.setRole(Role.USER);

        UserRecord createdUser = userService.createUser(newUser);
        UserResponse response = userService.convertToUserResponse(createdUser);
        return ResponseEntity.ok(response);
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

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest loginRequest) {
        return loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
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

    //    // Login a user
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody UserRequest loginRequest) {
//        // Handles the process of validating user credentials
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(
//                            loginRequest.getUsername(),
//                            loginRequest.getPassword()
//                    )
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            // Resets any failed login attempts after successful login authentication
//            userService.resetAndUnlockAccount(loginRequest.getUsername());
//
//            // Generates JWT token & responses
//            String jwt = tokenProvider.generateToken(authentication);
//            return ResponseEntity.ok().body("User logged in successfully with Token:" + jwt);
//
//        // Manages account lockout due to multiple failed login attempts
//        } catch (LockedException e) {
//            return ResponseEntity
//                    .status(HttpStatus.LOCKED)
//                    .body("Your account has been locked due to multiple failed login attempts. " +
//                            "Please contact support to unlock your account.");
//        } catch (BadCredentialsException e) {
//            userService.incrementFailedLoginAttempts(loginRequest.getUsername());
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid username or password.");
//        }
//    }
}
