package com.snapppay.tasks.expensetracker.domain.entities;

import com.snapppay.tasks.expensetracker.security.entities.AbstractAuditingEntity;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Expense entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ExpenseEntity extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Min(1L)
    private BigDecimal amount;

    private String description;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @NotNull
    private CategoryEntity category;
}
