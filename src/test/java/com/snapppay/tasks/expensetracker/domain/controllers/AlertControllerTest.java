package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.BaseIntegrationTest;
import com.snapppay.tasks.expensetracker.domain.dtos.AlertDto;
import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.domain.enums.AlertDuration;
import com.snapppay.tasks.expensetracker.domain.services.AlertService;
import com.snapppay.tasks.expensetracker.domain.services.CategoryService;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AlertControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlertService alertService;

    @Autowired
    private CategoryService categoryService;

    private CategoryDto testCategory;

    @BeforeEach
    @Transactional
    public void setup() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        userEntity.setPassword("{noop}password"); // Password should be encoded in real scenarios
        userEntity.setEmail("testuser@example.com");

        userRepository.save(userEntity);

        // Set up the security context with the test user
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userEntity, null, userEntity.getAuthorities());
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        // Add a test category
        testCategory = new CategoryDto();
        testCategory.setName("Test Category");
        testCategory.setDescription("Description for Test Category");
        testCategory = categoryService.addCategory(testCategory);

        // Add some test alerts
        AlertDto alert1 = new AlertDto();
        alert1.setAmount(50L);
        alert1.setDescription("Alert for Test 1");
        alert1.setCategory(testCategory);
        alert1.setDuration(AlertDuration.MONTHLY);

        AlertDto alert2 = new AlertDto();
        alert2.setAmount(100L);
        alert2.setDescription("Alert for Test 2");
        alert2.setCategory(testCategory);
        alert2.setDuration(AlertDuration.WEEKLY);

        alertService.addAlert(alert1, userEntity);
        alertService.addAlert(alert2, userEntity);
    }

    @Test
    public void testAddAlert() throws Exception {
        String alertJson = String.format(
                "{\"amount\":200.00,\"description\":\"New Alert\",\"category\":{\"id\":%d,\"name\":\"Test Category\"},\"duration\":\"DAILY\"}",
                testCategory.getId()
        );

        mockMvc.perform(post("/api/alerts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(alertJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(200)))
                .andExpect(jsonPath("$.description", is("New Alert")))
                .andExpect(jsonPath("$.category.name", is("Test Category")))
                .andExpect(jsonPath("$.duration", is("DAILY")));
    }

    @Test
    public void testGetAllAlerts() throws Exception {
        mockMvc.perform(get("/api/alerts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].amount", is(50)))
                .andExpect(jsonPath("$[0].description", is("Alert for Test 1")))
                .andExpect(jsonPath("$[0].category.name", is("Test Category")))
                .andExpect(jsonPath("$[0].duration", is("MONTHLY")))
                .andExpect(jsonPath("$[1].amount", is(100)))
                .andExpect(jsonPath("$[1].description", is("Alert for Test 2")))
                .andExpect(jsonPath("$[1].category.name", is("Test Category")))
                .andExpect(jsonPath("$[1].duration", is("WEEKLY")));
    }
}
