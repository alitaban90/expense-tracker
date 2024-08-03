package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Expense Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ExpenseDto extends AbstractAuditingDto {

    private Long id;

    @NotNull
    private Long amount;

    private String description;

    @NotNull
    private LocalDateTime localDateTime;

    @NotNull
    private CategoryDto categoryDto;

    public ExpenseDto() {
    }

    public ExpenseDto(ExpenseEntity expenseEntity) {
        this.id = expenseEntity.getId();
        this.amount = expenseEntity.getAmount();
        this.description = expenseEntity.getDescription();
        this.localDateTime = expenseEntity.getLocalDateTime();
        this.categoryDto = new CategoryDto(expenseEntity.getCategory());
    }
}
