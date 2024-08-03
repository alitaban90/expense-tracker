package com.snapppay.tasks.expensetracker.security.controllers;

import com.snapppay.tasks.expensetracker.security.dtos.AuthenticationResponse;
import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserLoginDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserRegisterDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.services.AuthenticationService;
import com.snapppay.tasks.expensetracker.security.services.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication")
public class AuthenticationController {

    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Validated @RequestBody UserRegisterDto userRegisterDto){
        UserDto registeredUser = authenticationService.register(userRegisterDto);
        return ResponseEntity.ok(registeredUser);
    }

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
