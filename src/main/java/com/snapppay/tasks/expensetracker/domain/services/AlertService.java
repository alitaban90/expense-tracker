package com.snapppay.tasks.expensetracker.domain.services;

import com.snapppay.tasks.expensetracker.domain.dtos.AlertDto;
import com.snapppay.tasks.expensetracker.domain.entities.AlertEntity;
import com.snapppay.tasks.expensetracker.domain.repositories.AlertRepository;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Alert service.
 */
@Service
public class AlertService {

    private final AlertRepository alertRepository;

    /**
     * Instantiates a new Alert service.
     *
     * @param alertRepository the alert repository
     */
    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    /**
     * Add alert alert dto.
     *
     * @param alertDto    the alert dto
     * @param currentUser the current user
     * @return the alert dto
     */
    @Transactional
    public AlertDto addAlert(AlertDto alertDto, UserEntity currentUser) {
        return new AlertDto(alertRepository.save(new AlertEntity(alertDto, currentUser)));
    }

    /**
     * Get all alerts list.
     *
     * @param currentUser the current user
     * @return the list
     */
    public List<AlertDto> getAllAlerts(UserEntity currentUser){
        return alertRepository.findAllByUserId(currentUser.getId()).stream().map(AlertDto::new).toList();
    }


}
