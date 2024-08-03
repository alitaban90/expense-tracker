package com.snapppay.tasks.expensetracker.security.controllers;

import com.snapppay.tasks.expensetracker.security.dtos.AuthenticationResponse;
import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserLoginDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserRegisterDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.services.AuthenticationService;
import com.snapppay.tasks.expensetracker.security.services.JwtService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * The type Authentication controller.
 */
@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param jwtService            the jwt service
     * @param authenticationService the authentication service
     */
    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    /**
     * Register response entity.
     *
     * @param userRegisterDto the user register dto
     * @return the response entity
     */
    @Operation(summary = "Register a new user", description = "This endpoint registers a new user with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Validated @RequestBody UserRegisterDto userRegisterDto) {
        UserDto registeredUser = authenticationService.register(userRegisterDto);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Authenticate response entity.
     *
     * @param userLoginDto the user login dto
     * @return the response entity
     */
    @Operation(summary = "Authenticate user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully authenticated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content)
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Validated @RequestBody UserLoginDto userLoginDto) {
        UserEntity userEntity = authenticationService.authenticate(userLoginDto);
        String jwtToken = jwtService.generateToken(userEntity);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwtToken(jwtToken);
        authenticationResponse.setExpirationTime(jwtService.getExpirationTime());
        return ResponseEntity.ok(authenticationResponse);
    }
}
