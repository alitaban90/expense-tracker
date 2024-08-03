package com.snapppay.tasks.expensetracker.controllers;

import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type User account controller.
 */
@RestController
@RequestMapping("/api/account")
public class UserAccountController {

    /**
     * Get account info response entity.
     *
     * @return the response entity
     */
    @GetMapping
    public ResponseEntity<UserDto> getAccountInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        UserEntity currentUser = (UserEntity) authentication.getPrincipal();

        return ResponseEntity.ok(new UserDto(currentUser));
    }
}
