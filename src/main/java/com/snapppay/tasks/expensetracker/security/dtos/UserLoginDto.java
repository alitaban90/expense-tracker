package com.snapppay.tasks.expensetracker.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User login dto.
 */
@Data
public class UserLoginDto {

    @Schema(description = "Username of the user", example = "johndoe")
    @NotBlank
    private String username;

    @Schema(description = "Password of the user", example = "password123")
    @NotBlank
    private String password;

}
