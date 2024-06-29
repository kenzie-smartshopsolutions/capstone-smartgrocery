package com.kenzie.appserver.controller;

import com.kenzie.appserver.config.Role;
import com.kenzie.appserver.config.auth.JwtTokenProvider;
import com.kenzie.appserver.controller.model.user.UserRequest;
import com.kenzie.appserver.controller.model.user.UserResponse;
import com.kenzie.appserver.repositories.model.LoginLog;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import com.kenzie.appserver.service.LoginService;
import com.kenzie.capstone.service.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kenzie.appserver.config.Role.USER;

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
        newUser.setRole(USER.toString());

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

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserRecord> users = userService.getAllUsers();
        List<UserResponse> userResponses = users.stream()
                .map(userService::convertToUserResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userResponses);
    }

    /**
     * API endpoints for User login date/time
     * **/

    // Get LoginLogs by userId
    @GetMapping("/login/logs/user/{userId}")
    public ResponseEntity<List<LoginLog>> getLoginLogsByUserId(@PathVariable String userId) {
        List<LoginLog> logs = loginService.getLoginLogsByUserId(userId);
        return ResponseEntity.ok(logs);
    }

    // Get LoginLogs by userId & Date
    @GetMapping("/login/logs/user/{userId}/date/{date}")
    public ResponseEntity<List<LoginLog>> getLoginLogsByUserIdAndTime(@PathVariable String userId, @PathVariable String date) {
        List<LoginLog> logs = loginService.getLoginLogsByUserIdAndTime(userId, date);
        return ResponseEntity.ok(logs);
    }

    // Get LoginLogs by Date
    @GetMapping("/login/logs/date/{date}")
    public ResponseEntity<List<LoginLog>> getLoginLogsByDate(@PathVariable String date) {
        List<LoginLog> logs = loginService.getLoginLogsByDate(date);
        return ResponseEntity.ok(logs);
    }
}
