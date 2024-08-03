package com.snapppay.tasks.expensetracker.security.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * The type User register dto.
 */
@Data
public class UserRegisterDto {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

}
