package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.domain.dtos.ExpenseDto;
import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Expense service.
 */
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final AlertEvaluationService alertEvaluationService;

    /**
     * Instantiates a new Expense service.
     *
     * @param expenseRepository the expense repository
     */
    public ExpenseService(ExpenseRepository expenseRepository, AlertEvaluationService alertEvaluationService) {
        this.expenseRepository = expenseRepository;
        this.alertEvaluationService = alertEvaluationService;
    }

    /**
     * Gets expenses.
     *
     * @param currentUser the current user
     * @return the expenses
     */
    public List<ExpenseDto> getExpenses(UserEntity currentUser) {
        return expenseRepository.findAllByUserId(currentUser.getId()).stream().map(ExpenseDto::new).toList();
    }

    /**
     * Add expense expense dto.
     *
     * @param expenseDto  the expense dto
     * @param currentUser the current user
     * @return the expense dto
     */
    @Transactional
    public ExpenseDto addExpense(ExpenseDto expenseDto, UserEntity currentUser) {
        ExpenseEntity expense = new ExpenseEntity(expenseDto, currentUser);
        alertEvaluationService.evaluateAlertsForExpense(expense);
        return new ExpenseDto(expenseRepository.save(expense));
    }

    /**
     * Gets expenses for month.
     *
     * @param currentUser the current user
     * @param startDate   the start date
     * @param endDate     the end date
     * @return the expenses for month
     */
    public List<ExpenseDto> getExpensesForMonth(UserEntity currentUser, LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findAllByUserIdAndLocalDateTimeBetween(currentUser.getId(), startDate, endDate).stream().map(ExpenseDto::new).toList();
    }
}
