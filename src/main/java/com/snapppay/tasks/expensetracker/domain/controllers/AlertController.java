package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.AlertDto;
import com.snapppay.tasks.expensetracker.domain.services.AlertService;
import com.snapppay.tasks.expensetracker.security.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Alert controller.
 */
@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    /**
     * Instantiates a new Alert controller.
     *
     * @param alertService the alert service
     */
    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    /**
     * Add alert response entity.
     *
     * @param alertDto the alert dto
     * @return the response entity
     */
    @PostMapping
    public ResponseEntity<AlertDto> addAlert(@Validated @RequestBody AlertDto alertDto) {
        return ResponseEntity.ok(alertService.addAlert(alertDto, SecurityUtils.getCurrentUser()));
    }

    /**
     * Gets all alerts.
     *
     * @return the all alerts
     */
    @GetMapping
    public ResponseEntity<List<AlertDto>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts(SecurityUtils.getCurrentUser()));
    }
}
