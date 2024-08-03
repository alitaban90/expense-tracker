package com.snapppay.tasks.expensetracker.security.utils;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * The type Security utils.
 */
public class SecurityUtils {

    /**
     * Get current user user entity.
     *
     * @return the user entity
     */
    public static UserEntity getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }
}
