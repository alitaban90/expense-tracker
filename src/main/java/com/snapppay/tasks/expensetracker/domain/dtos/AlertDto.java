package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import com.snapppay.tasks.expensetracker.domain.enums.AlertDuration;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type Alert Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Data Transfer Object representing an alert.")
public class AlertDto extends AbstractAuditingDto {

    @Schema(description = "Unique identifier for the alert", example = "1")
    private Long id;

    @NotNull
    @Schema(description = "Amount threshold for triggering the alert", example = "100")
    private Long amount;

    @Schema(description = "Description of the alert", example = "Alert for high spending")
    private String description;

    @Schema(description = "Category associated with the alert")
    private CategoryDto category;

    @Schema(description = "Duration of the alert trigger (e.g., DAILY, MONTHLY)")
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
