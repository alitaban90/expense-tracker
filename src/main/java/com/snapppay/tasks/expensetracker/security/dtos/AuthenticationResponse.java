package com.snapppay.tasks.expensetracker.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * The type Authentication response.
 */
@Data
public class AuthenticationResponse {

    @Schema(description = "JWT token generated for the authenticated user", example = "eyJhbGciOiJIUzI1NiIsInR...")
    private String jwtToken;

    @Schema(description = "Expiration time of the JWT token in milliseconds since epoch", example = "1629483939000")
    private Long expirationTime;
}
