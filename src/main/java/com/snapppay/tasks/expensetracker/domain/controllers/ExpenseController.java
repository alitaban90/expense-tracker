package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.ExpenseDto;
import com.snapppay.tasks.expensetracker.domain.services.ExpenseService;
import com.snapppay.tasks.expensetracker.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Add a new expense", description = "Creates a new expense and saves it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExpenseDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody @Validated ExpenseDto expense) {
        return ResponseEntity.ok(expenseService.addExpense(expense, SecurityUtils.getCurrentUser()));
    }

    /**
     * Gets expenses.
     *
     * @return the expenses
     */
    @Operation(summary = "Get all expenses", description = "Retrieves all expenses for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved expenses",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExpenseDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
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
    @Operation(summary = "Get monthly expenses", description = "Retrieves expenses for a specified month.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved monthly expenses",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ExpenseDto.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid month format", content = @Content)
    })
    @GetMapping("/report/monthly")
    public ResponseEntity<List<ExpenseDto>> getMonthlyExpenses(@RequestParam String month) {
        LocalDate startDate = LocalDate.parse(month + "-01");
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return ResponseEntity.ok(expenseService.getExpensesForMonth(SecurityUtils.getCurrentUser(), startDate.atStartOfDay(), endDate.atStartOfDay()));
    }
}
