package com.snapppay.tasks.expensetracker.security.dtos;

import lombok.Data;

/**
 * The type Authentication response.
 */
@Data
public class AuthenticationResponse {

    private String jwtToken;
    private Long expirationTime;
}
