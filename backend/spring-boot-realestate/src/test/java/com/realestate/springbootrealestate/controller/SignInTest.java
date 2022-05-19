package com.realestate.springbootrealestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.springbootrealestate.dto.request.LoginRequest;
import com.realestate.springbootrealestate.dto.request.SignupRequest;
import com.realestate.springbootrealestate.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
public class SignInTest {
    private static MockHttpServletRequest request;
    private static SignupRequest signupRequest;
    private static LoginRequest loginRequest;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${sql.script.create.ROLE_USER}")
    private String sqlAddUserRole;

    @Value("${sql.script.create.ROLE_MODERATOR}")
    private String sqlAddModeratorRole;

    @Value("${sql.script.create.ROLE_ADMIN}")
    private String sqlAddAdminRole;


    @BeforeAll
    public static void setup(){
        request = new MockHttpServletRequest();
        signupRequest = new SignupRequest();
        loginRequest = new LoginRequest();
    }


    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddUserRole);
        jdbc.execute(sqlAddModeratorRole);
        jdbc.execute(sqlAddAdminRole);

        signupRequest.setUsername("username1");
        signupRequest.setEmail("user@gmail.com");
        Set<String> role = new HashSet<String>(){{
            add("user");
        }};
        signupRequest.setRole(role);
        signupRequest.setPassword("password");
        signupRequest.setPhoneNumber("1234243");
        signupRequest.setFirstName("Dalma");
        signupRequest.setLastName("Doro");
    }


    @Test
    public void singInTestHttpRequestTest() throws Exception{
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.message", is("User registered successfully!")));

        loginRequest.setUsername("username1");
        loginRequest.setPassword("password");

        mockMvc.perform(post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().is2xxSuccessful());
    }
}
