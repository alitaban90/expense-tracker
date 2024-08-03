package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.ExpenseDto;
import com.snapppay.tasks.expensetracker.domain.services.ExpenseService;
import com.snapppay.tasks.expensetracker.security.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Expense controller.
 */
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    /**
     * Instantiates a new Expense controller.
     *
     * @param expenseService the expense service
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Add expense response entity.
     *
     * @param expense the expense
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody @Validated ExpenseDto expense) {
        return ResponseEntity.ok(expenseService.addExpense(expense, SecurityUtils.getCurrentUser()));
    }

    /**
     * Gets expenses.
     *
     * @return the expenses
     */
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getExpenses() {
        return ResponseEntity.ok(expenseService.getExpenses(SecurityUtils.getCurrentUser()));
    }

    /**
     * Gets monthly expenses.
     *
     * @param month the month
     * @return the monthly expenses
     */
    @GetMapping("/report/monthly")
    public ResponseEntity<List<ExpenseDto>> getMonthlyExpenses(@RequestParam String month) {
        LocalDate startDate = LocalDate.parse(month + "-01");
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return ResponseEntity.ok(expenseService.getExpensesForMonth(SecurityUtils.getCurrentUser(), startDate.atStartOfDay(), endDate.atStartOfDay()));
    }
}
