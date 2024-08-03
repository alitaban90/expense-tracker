package com.snapppay.tasks.expensetracker.security.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snapppay.tasks.expensetracker.security.dtos.UserLoginDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserRegisterDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIntegrationTest {

    @Value(("${security.jwt.expiration-time}"))
    private Long jwtExpiration;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testRegister() throws Exception {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("testuser");
        userRegisterDto.setPassword("password");

        mockMvc.perform(post("/api/authentication/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRegisterDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testAuthenticate() throws Exception {
        testRegister();

        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername("testuser");
        userLoginDto.setPassword("password");

        mockMvc.perform(post("/api/authentication/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userLoginDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken").exists())
                .andExpect(jsonPath("$.expirationTime").value(jwtExpiration));
    }
}