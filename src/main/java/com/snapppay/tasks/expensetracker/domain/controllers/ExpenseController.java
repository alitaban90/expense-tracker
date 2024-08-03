package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.ExpenseDto;
import com.snapppay.tasks.expensetracker.domain.services.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ExpenseDto addExpense(@RequestBody ExpenseDto expense) {
        return expenseService.addExpense(expense);
    }

    @GetMapping("/{userId}")
    public List<ExpenseDto> getExpenses(@PathVariable Long userId) {
        return expenseService.getExpenses(userId);
    }

    @GetMapping("/{userId}/monthly")
    public List<ExpenseDto> getMonthlyExpenses(@PathVariable Long userId, @RequestParam String month) {
        LocalDate startDate = LocalDate.parse(month + "-01");
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return expenseService.getExpensesForMonth(userId, startDate.atStartOfDay(), endDate.atStartOfDay());
    }
}
