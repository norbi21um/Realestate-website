package com.realestate.springbootrealestate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDetailsServiceImplTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    ObjectMapper objectMapper;

    @Value("${sql.script.create.user1}")
    private String sqlAddUser1;

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddUser1);
    }

    @Test
    public void loadUserByUsernameTest() throws Exception{
        UserDetails userDetails = userDetailsService.loadUserByUsername("user");
        assertNotNull(userDetails);
    }

    @Test
    public void loadUserByUsernameNonValidUserTest() throws Exception{
        assertThrows(UsernameNotFoundException.class, () -> {userDetailsService.loadUserByUsername("rosszUsername");}
                , "Should throw exception.");
    }

}
