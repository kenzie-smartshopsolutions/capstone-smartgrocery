package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.UserService;
import com.kenzie.appserver.service.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper = new ObjectMapper();

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        String userId = "105f1de6-03b7-4e5e-afbe-414f0a4301a7";
        sampleUser = new User(userId,
                "someguy",
                "P@ssw0rd",
                "someguy@email.com",
                "household"
                );

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("someguy", "P@ssw0rd"));

    }

    @Test
    void createUserTest() throws Exception {
        UserRecord sampleUserRecord = userService.convertFromDto(sampleUser);

        given(userService.createUser(sampleUser)).willReturn(sampleUserRecord);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(sampleUser.getUserId()));
    }

    @Test
    void getUserByIdTest() throws Exception {
        UserRecord sampleUserRecord = userService.convertFromDto(sampleUser);

        given(userService.getUserById(sampleUser.getUserId())).willReturn(sampleUserRecord);

        mockMvc.perform(get("/users/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(sampleUser.getUserId()));
    }

    @Test
    void updateUserTest() throws Exception {
        UserRecord sampleUserRecord = userService.convertFromDto(sampleUser);

        given(userService.updateUser(sampleUserRecord)).willReturn(sampleUserRecord);

        mockMvc.perform(put("/users/{id}", "105f1de6-03b7-4e5e-afbe-414f0a4301a7")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(sampleUser.getUsername()));
    }

    @Test
    void deleteUserTest() throws Exception {
        mockMvc.perform(delete("/users/{id}", sampleUser.getUserId()))
                .andExpect(status().isNoContent());
    }
}