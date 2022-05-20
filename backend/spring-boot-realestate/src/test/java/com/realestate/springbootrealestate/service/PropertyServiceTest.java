package com.realestate.springbootrealestate.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.dto.response.PropertyResponse;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.repository.PropertyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.notNull;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropertyServiceTest {
    private static MockHttpServletRequest request;
    private static PropertyRequest propertyRequest;


    @Autowired
    private JdbcTemplate jdbc;


    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private PropertyService propertyService;

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
    public void deletePropertyByIdTest(){
        Property property = propertyRepository.findById(1L).orElse(null);
        assertNotNull(property);

        propertyService.deletePropertyById(1L);

        Property property2 = propertyRepository.findById(1L).orElse(null);
        assertNull(property2);

    }

    @Test
    public void getPropertyByIdInvalidValueTest(){
        assertThrows(Exception.class, () -> {propertyService.getPropertyById(500L);}, "Should throw exception.");
    }

    @Test
    public void getPropertyByIdValidValueTest(){
        assertDoesNotThrow(() -> {propertyService.getPropertyById(1L);}, "Should not throw exception.");
        PropertyResponse property = propertyService.getPropertyById(1L);
        assertNotNull(property);
        assertEquals(1L, property.getId());
    }

    @Test
    public void getPropertyByIdClicksChangesTest(){
        assertDoesNotThrow(() -> {propertyService.getPropertyById(1L);}, "Should not throw exception.");
        PropertyResponse property = propertyService.getPropertyById(1L);
        assertNotNull(property);
        assertEquals(3, property.getNumberOfClicks());
        PropertyResponse property2 = propertyService.getPropertyById(1L);
        assertEquals(4, property2.getNumberOfClicks());
    }

    @Test
    public void createNewPropertyTest(){
        Property property = propertyService.createNewProperty(propertyRequest);
        assertNotNull(property);
    }

    @Test
    public void createNewPropertyInvalidUserIdTest(){
        propertyRequest.setUserId(400L);
        Property property = propertyService.createNewProperty(propertyRequest);
        assertNull(property);
    }

}
