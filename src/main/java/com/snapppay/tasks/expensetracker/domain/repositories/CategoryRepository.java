package com.snapppay.tasks.expensetracker.domain.repositories;

import com.snapppay.tasks.expensetracker.domain.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Category repository.
 */
@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
}
