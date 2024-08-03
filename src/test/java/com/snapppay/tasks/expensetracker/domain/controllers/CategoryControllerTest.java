package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.BaseIntegrationTest;
import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.domain.services.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest extends BaseIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    @Transactional
    public void setup() {
        CategoryDto category1 = new CategoryDto();
        category1.setName("Food");
        category1.setDescription("Food-related expenses");

        CategoryDto category2 = new CategoryDto();
        category2.setName("Transport");
        category2.setDescription("Transport-related expenses");

        categoryService.addCategory(category1);
        categoryService.addCategory(category2);
    }

    @Test
    @WithMockUser
    public void testAddCategory() throws Exception {
        CategoryDto newCategory = new CategoryDto();
        newCategory.setName("Entertainment");
        newCategory.setDescription("Entertainment-related expenses");

        mockMvc.perform(post("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Entertainment\",\"description\":\"Entertainment-related expenses\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Entertainment")))
                .andExpect(jsonPath("$.description", is("Entertainment-related expenses")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser
    public void testGetAllCategories() throws Exception {
        mockMvc.perform(get("/api/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Food")))
                .andExpect(jsonPath("$[0].description", is("Food-related expenses")))
                .andExpect(jsonPath("$[1].name", is("Transport")))
                .andExpect(jsonPath("$[1].description", is("Transport-related expenses")))
                .andDo(MockMvcResultHandlers.print());
    }
}

