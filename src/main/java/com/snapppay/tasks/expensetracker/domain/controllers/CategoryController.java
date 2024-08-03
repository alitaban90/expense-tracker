package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.domain.services.CategoryService;
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
    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody @Validated CategoryDto categoryDto){
        return ResponseEntity.ok(categoryService.addCategory(categoryDto));
    }

    /**
     * Get all categories list.
     *
     * @return the list
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
