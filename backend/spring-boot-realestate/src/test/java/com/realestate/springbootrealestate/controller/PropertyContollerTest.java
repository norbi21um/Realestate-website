package com.realestate.springbootrealestate.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realestate.springbootrealestate.dto.request.PropertyRequest;
import com.realestate.springbootrealestate.model.Property;
import com.realestate.springbootrealestate.repository.PropertyRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropertyContollerTest {

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    ObjectMapper objectMapper;


    @Value("${sql.script.create.click}")
    private String sqlAddClick;


    @Value("${sql.script.create.district}")
    private String sqlAddDistrict;

    @Value("${sql.script.create.user1}")
    private String sqlAddUser1;

    @Value("${sql.script.create.user2}")
    private String sqlAddUser2;

    @Value("${sql.script.create.property1}")
    private String sqlAddProperty1;

    @Value("${sql.script.create.property2}")
    private String sqlAddProperty2;

    @Value("${sql.script.create.property3}")
    private String sqlAddProperty3;

    @Value("${sql.script.create.property4}")
    private String sqlAddProperty4;



    public static final MediaType APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON;


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
    }

    @Test
    public void listAllPropertiesTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("size", "5")
                        .param("page", "0")
                        .param("sortBy", ""))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].id", is(1)));
    }

    @Test
    public void listAllPropertiesByPriceAscTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("size", "5")
                        .param("page", "0")
                        .param("sortBy", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].price", is(10000.0)));
    }

    @Test
    public void listAllPropertiesByPricePopularityTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("size", "5")
                        .param("page", "0")
                        .param("sortBy", "popuparity"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].numberOfClicks", is(5)));
    }

    //TODO: Number of clicks miatt error-t dob ha "null", de azt amúgy is ki kell majd törölni
    @Test
    public void PropertyInformationHttpRequestTest() throws Exception {
        Optional<Property> student = propertyRepository.findById(1L);

        assertTrue(student.isPresent());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.address", is("Brody Sandor utca 46")))
                .andExpect(jsonPath("$.price", is(40000.0)))
                .andExpect(jsonPath("$.area", is(45.0)))
                .andExpect(jsonPath("$.user.id", is(1)));;
    }

   @Test
    public void searchPropertyHttpRequestTest() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/properties/searchByKeyword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("size", "5")
                        .param("page", "0")
                        .param("district", "V")
                        .param("address", "")
                        .param("sortBy", "asc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content", hasSize(4)))
                .andExpect(jsonPath("$.content[0].id", is(4)));
    }

}

