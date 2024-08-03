package com.snapppay.tasks.expensetracker.domain.repositories;

import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Alert repository.
 */
@Repository
public interface AlertRepository extends JpaRepository<AlertEntity, Long> {
}
