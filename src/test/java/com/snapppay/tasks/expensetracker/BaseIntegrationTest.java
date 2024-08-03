package com.snapppay.tasks.expensetracker;


import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.CategoryRepository;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import com.snapppay.tasks.expensetracker.domain.services.AlertService;
import com.snapppay.tasks.expensetracker.security.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * The type Base integration test.
 */
@SpringBootTest
@AutoConfigureMockMvc
public abstract class BaseIntegrationTest {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private CategoryEntity testCategory;

    @Autowired
    private AlertService alertService;

    /**
     * Base setup.
     */
    @BeforeEach
    public void base_setup() {
        // Clear all existing data
        alertService.deleteAllAlerts();
        expenseRepository.deleteAll();
        categoryRepository.deleteAll();
        userRepository.deleteAll();
    }
}
