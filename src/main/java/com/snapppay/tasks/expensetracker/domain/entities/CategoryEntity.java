package com.snapppay.tasks.expensetracker.domain.entities;

import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.security.entities.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The type Category entity.
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class CategoryEntity extends AbstractAuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    /**
     * Instantiates a new Category entity.
     */
    public CategoryEntity() {
    }

    /**
     * Instantiates a new Category entity.
     *
     * @param categoryDto the category dto
     */
    public CategoryEntity(CategoryDto categoryDto) {
        this.id = categoryDto.getId();
        this.name = categoryDto.getName();
        this.description = categoryDto.getDescription();
    }
}
