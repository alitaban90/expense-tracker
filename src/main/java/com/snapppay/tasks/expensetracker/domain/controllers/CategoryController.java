package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.domain.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Category controller.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Instantiates a new Category controller.
     *
     * @param categoryService the category service
     */
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * Add category category dto.
     *
     * @param categoryDto the category dto
     * @return the category dto
     */
    @Operation(summary = "Add a new category", description = "Creates a new category and saves it.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CategoryDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Validated CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    /**
     * Get all categories list.
     *
     * @return the list
     */
    @Operation(summary = "Get all categories", description = "Retrieves all categories.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved categories",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CategoryDto.class)))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
