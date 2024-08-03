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

@Service
public class AlertEvaluationService {
    private final AlertRepository alertRepository;
    private final ExpenseRepository expenseRepository;
    private final NotificationService notificationService;

    public AlertEvaluationService(AlertRepository alertRepository, ExpenseRepository expenseRepository, NotificationService notificationService) {
        this.alertRepository = alertRepository;
        this.expenseRepository = expenseRepository;
        this.notificationService = notificationService;
    }

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
            if(expenseEntities.stream().mapToLong(ExpenseEntity::getAmount).sum() > alert.getAmount()){
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
