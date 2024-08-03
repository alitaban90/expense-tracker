package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * The type Expense Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Data Transfer Object representing an expense.")
public class ExpenseDto extends AbstractAuditingDto {

    @Schema(description = "Unique identifier for the expense", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Amount of the expense", example = "12345")
    private Long amount;

    @Schema(description = "Description of the expense", example = "Dinner at restaurant")
    private String description;

    @NotNull
    @Schema(description = "Date and time of the expense", example = "2024-08-15T19:30:00")
    private LocalDateTime localDateTime;

    @NotNull
    @Schema(description = "Category associated with the expense")
    private CategoryDto categoryDto;

    /**
     * Instantiates a new Expense dto.
     */
    public ExpenseDto() {
    }

    /**
     * Instantiates a new Expense dto.
     *
     * @param expenseEntity the expense entity
     */
    public ExpenseDto(ExpenseEntity expenseEntity) {
        this.id = expenseEntity.getId();
        this.amount = expenseEntity.getAmount();
        this.description = expenseEntity.getDescription();
        this.localDateTime = expenseEntity.getLocalDateTime();
        this.categoryDto = new CategoryDto(expenseEntity.getCategory());
    }
}
