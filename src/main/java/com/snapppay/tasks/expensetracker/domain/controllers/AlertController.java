package com.snapppay.tasks.expensetracker.domain.controllers;

import com.snapppay.tasks.expensetracker.domain.dtos.AlertDto;
import com.snapppay.tasks.expensetracker.domain.services.AlertService;
import com.snapppay.tasks.expensetracker.security.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Add a new alert", description = "Creates a new alert and saves it for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AlertDto> addAlert(@Validated @RequestBody AlertDto alertDto) {
        return ResponseEntity.ok(alertService.addAlert(alertDto, SecurityUtils.getCurrentUser()));
    }

    /**
     * Gets all alerts.
     *
     * @return the all alerts
     */
    @Operation(summary = "Get all alerts", description = "Retrieves all alerts for the current user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved alerts",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AlertDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<AlertDto>> getAllAlerts() {
        return ResponseEntity.ok(alertService.getAllAlerts(SecurityUtils.getCurrentUser()));
    }
}
