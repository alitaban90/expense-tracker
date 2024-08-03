package com.snapppay.tasks.expensetracker.domain.services;


import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.domain.enums.AlertDuration;
import com.snapppay.tasks.expensetracker.domain.repositories.AlertRepository;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;

import java.util.List;

import static org.mockito.Mockito.*;

/**
 * The type Alert evaluation service test.
 */
public class AlertEvaluationServiceTest {

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private AlertEvaluationService alertEvaluationService;

    private UserEntity testUser;
    private CategoryEntity testCategory;
    private ExpenseEntity testExpense;
    private AlertEntity testAlert;

    /**
     * Sets .
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Set up a test user
        testUser = new UserEntity();
        testUser.setId(1L);
        testUser.setUsername("testuser");

        // Set up a test category
        testCategory = new CategoryEntity();
        testCategory.setId(1L);
        testCategory.setName("Test Category");

        // Set up a test expense
        testExpense = new ExpenseEntity();
        testExpense.setId(1L);
        testExpense.setUser(testUser);
        testExpense.setAmount(150L);
        testExpense.setCategory(testCategory);
        testExpense.setLocalDateTime(LocalDateTime.now());

        // Set up a test alert
        testAlert = new AlertEntity();
        testAlert.setId(1L);
        testAlert.setUser(testUser);
        testAlert.setAmount(100L);
        testAlert.setCategory(testCategory);
        testAlert.setDuration(AlertDuration.MONTHLY);
        testAlert.setDescription("Test Alert");
    }

    /**
     * Test evaluate alerts for expense with category.
     */
    @Test
    public void testEvaluateAlertsForExpenseWithCategory() {
        // Set up the repository mock behavior
        when(alertRepository.findAllByUserId(testUser.getId())).thenReturn(List.of(testAlert));
        when(expenseRepository.findAllByUserIdAndCategoryIdAndLocalDateTimeBetween(
                eq(testUser.getId()), eq(testCategory.getId()), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(testExpense));

        // Call the method to test
        alertEvaluationService.evaluateAlertsForExpense(testExpense);

        // Verify the notification was sent
        verify(notificationService, times(1)).sendNotification(testUser, "Test Alert");
    }

    /**
     * Test evaluate alerts for expense without category.
     */
    @Test
    public void testEvaluateAlertsForExpenseWithoutCategory() {
        testAlert.setCategory(null);

        // Set up the repository mock behavior
        when(alertRepository.findAllByUserId(testUser.getId())).thenReturn(List.of(testAlert));
        when(expenseRepository.findAllByUserIdAndLocalDateTimeBetween(
                eq(testUser.getId()), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(testExpense));

        // Call the method to test
        alertEvaluationService.evaluateAlertsForExpense(testExpense);

        // Verify the notification was sent
        verify(notificationService, times(1)).sendNotification(testUser, "Test Alert");
    }

    /**
     * Test evaluate alerts for expense no alert triggered.
     */
    @Test
    public void testEvaluateAlertsForExpenseNoAlertTriggered() {
        testExpense.setAmount(50L);

        // Set up the repository mock behavior
        when(alertRepository.findAllByUserId(testUser.getId())).thenReturn(List.of(testAlert));
        when(expenseRepository.findAllByUserIdAndCategoryIdAndLocalDateTimeBetween(
                eq(testUser.getId()), eq(testCategory.getId()), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(testExpense));

        // Call the method to test
        alertEvaluationService.evaluateAlertsForExpense(testExpense);

        // Verify the notification was not sent
        verify(notificationService, times(0)).sendNotification(testUser, "Test Alert");
    }

    /**
     * Test evaluate alerts for expense with multiple expenses.
     */
    @Test
    public void testEvaluateAlertsForExpenseWithMultipleExpenses() {
        ExpenseEntity anotherExpense = new ExpenseEntity();
        anotherExpense.setId(2L);
        anotherExpense.setUser(testUser);
        anotherExpense.setAmount(60L);
        anotherExpense.setCategory(testCategory);
        anotherExpense.setLocalDateTime(LocalDateTime.now());

        // Set up the repository mock behavior
        when(alertRepository.findAllByUserId(testUser.getId())).thenReturn(List.of(testAlert));
        when(expenseRepository.findAllByUserIdAndCategoryIdAndLocalDateTimeBetween(
                eq(testUser.getId()), eq(testCategory.getId()), any(LocalDateTime.class), any(LocalDateTime.class)
        )).thenReturn(List.of(testExpense, anotherExpense));

        // Call the method to test
        alertEvaluationService.evaluateAlertsForExpense(testExpense);

        // Verify the notification was sent
        verify(notificationService, times(1)).sendNotification(testUser, "Test Alert");
    }
}

