package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.domain.dtos.ExpenseDto;
import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.ExpenseRepository;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDto> getExpenses(Long userId) {
        return expenseRepository.findAllByUserId(userId).stream().map(ExpenseDto::new).toList();
    }

    @Transactional
    public ExpenseDto addExpense(ExpenseDto expenseDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = (UserEntity) authentication.getPrincipal();
        ExpenseEntity expense = new ExpenseEntity(expenseDto, currentUser);
        return new ExpenseDto(expenseRepository.save(expense));
    }

    public List<ExpenseDto> getExpensesForMonth(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findAllByUserIdAndLocalDateTimeBetween(userId, startDate, endDate).stream().map(ExpenseDto::new).toList();
    }
}
