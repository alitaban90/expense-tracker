package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The type Category Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CategoryDto extends AbstractAuditingDto {

    private Long id;

    @NotBlank
    private String name;

    private String description;

    public CategoryDto() {
    }

    public CategoryDto(CategoryEntity categoryEntity) {
        this.id = categoryEntity.getId();
        this.name = categoryEntity.getName();
        this.description = categoryEntity.getDescription();
    }
}
