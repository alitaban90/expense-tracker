package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import com.snapppay.tasks.expensetracker.domain.enums.AlertDuration;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * The type Alert Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AlertDto extends AbstractAuditingDto {

    private Long id;

    @NotNull
    private Long amount;

    private String description;

    private CategoryDto category;

    private AlertDuration duration;

    /**
     * Instantiates a new Alert dto.
     */
    public AlertDto() {
    }

    /**
     * Instantiates a new Alert dto.
     *
     * @param alertEntity the alert entity
     */
    public AlertDto(AlertEntity alertEntity) {
        this.id = alertEntity.getId();
        this.amount = alertEntity.getAmount();
        this.description = alertEntity.getDescription();
        this.category = new CategoryDto(alertEntity.getCategory());
        this.duration = alertEntity.getDuration();
    }
}
