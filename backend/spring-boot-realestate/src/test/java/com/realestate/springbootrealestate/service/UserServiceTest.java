package com.realestate.springbootrealestate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.dto.response.UserResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.model.User;
import com.realestate.springbootrealestate.repository.PropertyRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.when;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {
    private static MockHttpServletRequest request;
    private static PropertyRequest propertyRequest;


    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private StatisticsService statisticsService;

    @MockBean
    private PasswordEncoder encoder;

    @Autowired
    ObjectMapper objectMapper;


    @Value("${sql.script.create.click}")
    private String sqlAddClick;


    @Value("${sql.script.create.district}")
    private String sqlAddDistrict;

    @Value("${sql.script.create.user1}")
    private String sqlAddUser1;

    @Value("${sql.script.create.property1}")
    private String sqlAddProperty1;

    @Value("${sql.script.create.property2}")
    private String sqlAddProperty2;

    @Value("${sql.script.create.property3}")
    private String sqlAddProperty3;

    @Value("${sql.script.create.property4}")
    private String sqlAddProperty4;

    @BeforeAll
    public static void setup(){
        request = new MockHttpServletRequest();
        propertyRequest = new PropertyRequest();
    }

    @BeforeEach
    public void setupDatabase() {
        jdbc.execute(sqlAddDistrict);
        jdbc.execute(sqlAddUser1);
        jdbc.execute(sqlAddDistrict);
        jdbc.execute(sqlAddProperty1);
        jdbc.execute(sqlAddProperty2);
        jdbc.execute(sqlAddProperty3);
        jdbc.execute(sqlAddProperty4);
        jdbc.execute(sqlAddClick);

        propertyRequest.setAddress("address");
        propertyRequest.setDistrict("V");
        propertyRequest.setPrice(BigDecimal.valueOf(300));
        propertyRequest.setArea(BigDecimal.valueOf(30));
        propertyRequest.setImageUrl("imageUrl");
        propertyRequest.setDescription("Leiras");
        propertyRequest.setUserId(1L);
    }


    @Test
    public void getUserByIdTest() throws Exception{
        when(statisticsService.calculateOptimalHourToPost()).thenReturn(5);
        when(statisticsService.getUserReachThisMonth(notNull())).thenReturn(5);

        UserResponse userResponse = userService.getUserById(1L);

        assertNotNull(userResponse);
        assertEquals(userResponse.getFirstName(), "Norbert", "First names should match");
        assertEquals(userResponse.getLastName(), "Maszlag", "Last names should match");
        assertEquals(userResponse.getEmail(), "norbi@gmail.com", "Emails should match");
        assertEquals(userResponse.getId(), 1L, "Ids should match");
    }

    @Test
    public void convertToUserResponseNonValidUserTest() throws Exception{
        assertThrows(Exception.class, () -> {userService.convertToUserResponse(null, 1L);}, "Should throw exception.");
    }

    @Test
    public void updateUserPasswordTest() throws Exception{
        when(encoder.matches("password", "password"))
                .thenReturn(true);
        assertDoesNotThrow(() -> {userService.updateUserPassword(1L,"password", "password");}
                , "Should not throw exception.");
    }

    @Test
    public void updateUserPasswordInvalidPasswordTest() throws Exception{
        when(encoder.matches(notNull(), notNull()))
                .thenReturn(false);
        assertThrows(Exception.class, () -> {userService.updateUserPassword(1L,"password", "password1");}
                , "Should throw exception.");
    }

    @Test
    public void updateUsenameTest() throws Exception{
        when(encoder.matches("password", "password"))
                .thenReturn(true);
        assertDoesNotThrow(() -> {userService.updateUsername(1L,"password", "password");}
                , "Should not throw exception.");
    }

    @Test
    public void updateUsernameInvalidPasswordTest() throws Exception{
        when(encoder.matches(notNull(), notNull()))
                .thenReturn(false);
        assertThrows(Exception.class, () -> {userService.updateUsername(1L,"password", "password1");}
                , "Should throw exception.");
    }

    @Test
    public void deleteUserByIdTest() throws Exception{
        User user1 = userRepository.findById(1L).orElse(null);
        assertNotNull(user1);

        userService.deleteUserById(1L);

        User user2 = userRepository.findById(1L).orElse(null);
        assertNull(user2);
    }
}

