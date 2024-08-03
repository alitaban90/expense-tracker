package com.snapppay.tasks.expensetracker.domain.repositories;

import com.snapppay.tasks.expensetracker.domain.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The interface Expense repository.
 */
@Repository
public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {
    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<ExpenseEntity> findAllByUserId(Long userId);

    /**
     * Find all by user id and local date time between list.
     *
     * @param userId    the user id
     * @param startDate the start date
     * @param endDate   the end date
     * @return the list
     */
    List<ExpenseEntity> findAllByUserIdAndLocalDateTimeBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
