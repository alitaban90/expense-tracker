package com.snapppay.tasks.expensetracker.domain.repositories;

import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Alert repository.
 */
@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, Long> {

    /**
     * Find all by user id list.
     *
     * @param userId the user id
     * @return the list
     */
    List<AlertEntity> findAllByUserId(Long userId);
}
