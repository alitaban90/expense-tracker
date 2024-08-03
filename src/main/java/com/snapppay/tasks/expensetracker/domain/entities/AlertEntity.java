package com.snapppay.tasks.expensetracker.domain.entities;

import com.snapppay.tasks.expensetracker.domain.dtos.AlertDto;
import com.snapppay.tasks.expensetracker.domain.enums.AlertDuration;
import com.snapppay.tasks.expensetracker.security.entities.AbstractAuditingEntity;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * The type Alert entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class AlertEntity extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Min(1L)
    private BigDecimal amount;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    private AlertDuration duration;

    /**
     * Instantiates a new Alert entity.
     */
    public AlertEntity() {
    }

    /**
     * Instantiates a new Alert entity.
     *
     * @param alertDto    the alert dto
     * @param currentUser the current user
     */
    public AlertEntity(AlertDto alertDto, UserEntity currentUser) {
        this.id = alertDto.getId();
        this.amount = alertDto.getAmount();
        this.description = alertDto.getDescription();
        this.user = currentUser;
        this.category = new CategoryEntity(alertDto.getCategory());
        this.duration = alertDto.getDuration();
    }
}
