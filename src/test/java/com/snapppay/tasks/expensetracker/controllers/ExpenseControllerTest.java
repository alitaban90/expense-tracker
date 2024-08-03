package com.snapppay.tasks.expensetracker.controllers;

import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.CategoryRepository;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.repositories.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Expense controller test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private CategoryEntity testCategory;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        // Clear all existing data
        expenseRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();

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

        // Set up a test category
        testCategory = new CategoryEntity();
        testCategory.setName("Test Category");
        testCategory.setDescription("Description for Test Category");
        categoryRepository.save(testCategory);

        // Set up a test expense
        ExpenseEntity testExpense = new ExpenseEntity();
        testExpense.setAmount(new BigDecimal("50.00"));
        testExpense.setDescription("Test Expense");
        testExpense.setLocalDateTime(LocalDateTime.now());
        testExpense.setCategory(testCategory);
        testExpense.setUser(userEntity);
        expenseRepository.save(testExpense);
    }

    /**
     * Test add expense.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAddExpense() throws Exception {
        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":50.00,\"description\":\"Test Expense\",\"localDateTime\":\"" + LocalDateTime.now() + "\",\"categoryDto\":{\"id\":" + testCategory.getId() + ",\"name\":\"Test Category\"}}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", is(50.00)))
                .andExpect(jsonPath("$.description", is("Test Expense")))
                .andExpect(jsonPath("$.categoryDto.name", is("Test Category")));
    }

    /**
     * Test get expenses.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetExpenses() throws Exception {
        mockMvc.perform(get("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount", is(50.00)))
                .andExpect(jsonPath("$[0].description", is("Test Expense")))
                .andExpect(jsonPath("$[0].categoryDto.name", is("Test Category")));
    }

    /**
     * Test get monthly expenses.
     *
     * @throws Exception the exception
     */
    @Test
    public void testGetMonthlyExpenses() throws Exception {
        String month = LocalDateTime.now().getYear() + "-" + StringUtils.leftPad(LocalDateTime.now().getMonthValue() + "", 2, "0");

        mockMvc.perform(get("/api/expenses/report/monthly")
                        .param("month", month)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].amount", is(50.00)))
                .andExpect(jsonPath("$[0].description", is("Test Expense")))
                .andExpect(jsonPath("$[0].categoryDto.name", is("Test Category")));
    }
}

