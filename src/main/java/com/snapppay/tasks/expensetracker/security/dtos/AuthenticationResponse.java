package com.snapppay.tasks.expensetracker.security.dtos;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String jwtToken;
    private Long expirationTime;
}
