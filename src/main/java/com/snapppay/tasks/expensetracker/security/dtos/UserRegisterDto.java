package com.snapppay.tasks.expensetracker.security.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User register dto.
 */
@Data
public class UserRegisterDto {

    @Schema(description = "Username of the user", example = "john_doe")
    @NotBlank
    private String username;

    @Schema(description = "Password of the user", example = "password123")
    @NotBlank
    private String password;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Mobile number of the user", example = "+1234567890")
    private String mobile;

}
