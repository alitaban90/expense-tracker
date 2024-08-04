package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.AlertRepository;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Alert evaluation service.
 */
@Service
public class AlertEvaluationService {
    private final AlertRepository alertRepository;
    private final ExpenseRepository expenseRepository;
    private final NotificationService notificationService;

    /**
     * Instantiates a new Alert evaluation service.
     *
     * @param alertRepository     the alert repository
     * @param expenseRepository   the expense repository
     * @param notificationService the notification service
     */
    public AlertEvaluationService(AlertRepository alertRepository, ExpenseRepository expenseRepository, NotificationService notificationService) {
        this.alertRepository = alertRepository;
        this.expenseRepository = expenseRepository;
        this.notificationService = notificationService;
    }

    /**
     * Evaluates alerts based on the provided expense.
     *
     * This method checks all alerts associated with the user of the given expense and determines if any alert 
     * conditions are met. If an alert's condition is met, a notification is sent to the user.
     *
     * The evaluation process involves:
     * 1. Retrieving all alerts associated with the user of the provided expense.
     * 2. For each alert:
     *    - If the alert has no specific category, retrieves all expenses for the user within the time range defined by the alert's duration.
     *    - If the alert has a specific category, retrieves all expenses for that category within the time range defined by the alert's duration.
     * 3. Calculates the total amount of expenses within the time range and adds the amount of the provided expense.
     * 4. Compares the total amount to the threshold defined by the alert.
     * 5. If the total amount exceeds the alert's threshold, sends a notification to the user.
     *
     * @param expense The expense entity to evaluate against existing alerts. Must not be {@code null}.
     *
     * @throws NullPointerException if the provided expense is {@code null} or if any of its required fields are {@code null}.
     */
    public void evaluateAlertsForExpense(ExpenseEntity expense) {
        List<AlertEntity> alerts = alertRepository.findAllByUserId(expense.getUser().getId());
        for (AlertEntity alert : alerts) {
            List<ExpenseEntity> expenseEntities = new ArrayList<>();
            if (alert.getCategory() == null) {
                LocalDateTime startDate = getStartDate(alert);
                expenseEntities = expenseRepository.findAllByUserIdAndLocalDateTimeBetween(expense.getUser().getId(), startDate, LocalDateTime.now());
            } else if (expense.getCategory().getId().equals(alert.getCategory().getId())) {
                LocalDateTime startDate = getStartDate(alert);
                expenseEntities = expenseRepository.findAllByUserIdAndCategoryIdAndLocalDateTimeBetween(expense.getUser().getId(), alert.getCategory().getId(),
                        startDate, LocalDateTime.now());
            }
            if(expenseEntities.stream().mapToLong(ExpenseEntity::getAmount).sum() + expense.getAmount() > alert.getAmount()){
                notificationService.sendNotification(expense.getUser(), alert.getDescription());
            }
        }
    }

    private static LocalDateTime getStartDate(AlertEntity alert) {
        LocalDateTime startDate;
        switch (alert.getDuration()) {
            case DAILY -> startDate = LocalDateTime.now().with(LocalTime.MIN);
            case MONTHLY -> startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())
                    .with(LocalTime.MIN);
            case WEEKLY -> startDate = LocalDateTime.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                    .with(LocalTime.MIN);
            case YEARLY -> startDate = LocalDateTime.now().with(TemporalAdjusters.firstDayOfYear())
                    .with(LocalTime.MIN);
            case null -> startDate = LocalDateTime.MIN;
        }
        return startDate;
    }
}
