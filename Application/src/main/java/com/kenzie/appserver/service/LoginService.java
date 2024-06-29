package com.kenzie.appserver.service;

import com.kenzie.appserver.config.auth.JwtTokenProvider;
import com.kenzie.appserver.repositories.LoginLogRepository;
import com.kenzie.appserver.repositories.model.LoginLog;


import com.kenzie.capstone.service.model.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;
    private final LoginLogRepository loginLogRepository;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager,
                        UserService userService,
                        JwtTokenProvider tokenProvider,
                        LoginLogRepository loginLogRepository) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.loginLogRepository = loginLogRepository;
    }

    public ResponseEntity<?> login(String username, String password) {
        try {
            UserData userData = userService.getUserByUsername(username);
            if (userData == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Invalid username or password.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Resets failed Login attempts
            userService.resetAndUnlockAccount(username);

            String jwt = tokenProvider.generateToken(authentication);

            // Log the successful login attempt
            LoginLog loginLog = new LoginLog();
            String userId = userData.getUserId();
            String logId = UUID.randomUUID().toString();
            loginLog.setLogId(logId);
            loginLog.setUserId(userId);
            loginLog.setUsername(username);
            loginLog.setLoginDate(String.valueOf(LocalDateTime.now()));
            loginLogRepository.save(loginLog);

            return ResponseEntity.ok().body("User logged in successfully with Token:" + jwt);
        } catch (LockedException e) {
            return ResponseEntity
                    .status(HttpStatus.LOCKED)
                    .body("Your account has been locked due to multiple failed login attempts. " +
                            "Please contact support to unlock your account.");
        } catch (BadCredentialsException e) {
            userService.incrementFailedLoginAttempts(username);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password.");
        }
    }

    // Method to retrieve login logs by user ID
    public List<LoginLog> getLoginsByUserId(String userId) {
        return loginLogRepository.findByUserId(userId);
    }

    // Method to retrieve login logs by user ID and date
    public List<LoginLog> getLoginsByUserIdAndDate(String userId, String date) {
        return loginLogRepository.findByUserIdAndLoginDate(userId, date);
    }

    // Method to retrieve login logs by date
    public List<LoginLog> getLoginDate(String date) {
        return loginLogRepository.findByLoginDate(date);
    }

    public List<LoginLog> getAllLogins() {
        return loginLogRepository.findAll();
    }

    public List<LoginLog> getAllLoginsByUsername(String username) {
        return loginLogRepository.findByUsername(username);
    }
}
