package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.domain.dtos.CategoryDto;
import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Category service.
 */
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * Instantiates a new Category service.
     *
     * @param categoryRepository the category repository
     */
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Add category category dto.
     *
     * @param categoryDto the category dto
     * @return the category dto
     */
    @Transactional
    public CategoryDto addCategory(CategoryDto categoryDto){
        CategoryEntity categoryEntity = new CategoryEntity(categoryDto);
        return new CategoryDto(categoryRepository.save(categoryEntity));
    }

    /**
     * Get all categories list.
     *
     * @return the list
     */
    public List<CategoryDto> getAllCategories(){
        return categoryRepository.findAll().stream().map(CategoryDto::new).toList();
    }
}
