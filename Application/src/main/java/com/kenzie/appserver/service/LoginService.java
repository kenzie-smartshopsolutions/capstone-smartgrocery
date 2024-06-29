package com.kenzie.appserver.service;

import com.kenzie.appserver.config.auth.JwtTokenProvider;
import com.kenzie.appserver.service.UserService;

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

import javax.inject.Inject;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager,
                        UserService userService,
                        JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    public ResponseEntity<?> login(String username, String password) {
        try {
            UserData userData = userService.getUserByUsername(username);
            if (userData == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Resets failed Login attempts
            userService.resetAndUnlockAccount(username);

            String jwt = tokenProvider.generateToken(authentication);
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
}
