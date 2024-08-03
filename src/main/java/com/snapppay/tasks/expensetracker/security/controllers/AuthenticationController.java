package com.snapppay.tasks.expensetracker.security.controllers;

import com.snapppay.tasks.expensetracker.security.dtos.AuthenticationResponse;
import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserLoginDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserRegisterDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.services.AuthenticationService;
import com.snapppay.tasks.expensetracker.security.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Validated @RequestBody UserRegisterDto userRegisterDto){
        UserDto registeredUser = authenticationService.register(userRegisterDto);
        return ResponseEntity.ok(registeredUser);
    }

    /**
     * Authenticate response entity.
     *
     * @param userLoginDto the user login dto
     * @return the response entity
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@Validated @RequestBody UserLoginDto userLoginDto){
        UserEntity userEntity = authenticationService.authenticate(userLoginDto);
        String jwtToken = jwtService.generateToken(userEntity);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwtToken(jwtToken);
        authenticationResponse.setExpirationTime(jwtService.getExpirationTime());
        return ResponseEntity.ok(authenticationResponse);
    }
}
