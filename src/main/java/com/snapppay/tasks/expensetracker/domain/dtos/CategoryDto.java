package com.snapppay.tasks.expensetracker.domain.dtos;

import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.security.dtos.AbstractAuditingDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * The type Category Dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "Data Transfer Object representing a category.")
public class CategoryDto extends AbstractAuditingDto {

    @Schema(description = "Unique identifier for the category", example = "1")
    private Long id;

    @NotBlank
    @Schema(description = "Name of the category", example = "Food")
    private String name;

    @Schema(description = "Description of the category", example = "Expenses related to food and dining.")
    private String description;

    /**
     * Instantiates a new Category dto.
     */
    public CategoryDto() {
    }

    /**
     * Instantiates a new Category dto.
     *
     * @param categoryEntity the category entity
     */
    public CategoryDto(CategoryEntity categoryEntity) {
        this.id = categoryEntity.getId();
        this.name = categoryEntity.getName();
        this.description = categoryEntity.getDescription();
    }
}
