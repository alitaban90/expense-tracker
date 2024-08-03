package com.snapppay.tasks.expensetracker.security.dtos;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * The type Abstract auditing dto.
 */
@Data
public class AbstractAuditingDto {

    private String createdBy;

    private LocalDateTime createdDate;

    private String lastModifiedBy;

    private LocalDateTime lastModifiedDate;
}
