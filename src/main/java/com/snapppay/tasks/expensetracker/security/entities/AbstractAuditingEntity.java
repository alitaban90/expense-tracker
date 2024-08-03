package com.snapppay.tasks.expensetracker.security.entities;

import java.io.Serial;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Base abstract class for entities which will hold definitions for created, last modified by and created,
 * last modified by date.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class AbstractAuditingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @CreatedBy
    @Column(length = 50, updatable = false)
    @JsonIgnore
    private String createdBy;

    @CreatedDate
    @JsonIgnore
    private LocalDateTime createdDate;

    @LastModifiedBy
    @Column(length = 50)
    @JsonIgnore
    private String lastModifiedBy;

    @LastModifiedDate
    @JsonIgnore
    private LocalDateTime lastModifiedDate;
}