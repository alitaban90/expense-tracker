package com.snapppay.tasks.expensetracker.controllers;

import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class UserAccountController {

    @GetMapping
    public ResponseEntity<UserDto> getAccountInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(new UserDto(currentUser));
    }
}
