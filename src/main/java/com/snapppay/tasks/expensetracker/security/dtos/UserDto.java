package com.snapppay.tasks.expensetracker.security.dtos;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type User dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AbstractAuditingDto {

    @Schema(description = "ID of the user", example = "1")
    private Long id;

    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Email of the user", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Mobile number of the user", example = "+1234567890")
    private String mobile;

    /**
     * Instantiates a new User dto.
     */
    public UserDto() {
    }

    /**
     * Instantiates a new User dto.
     *
     * @param userEntity the user entity
     */
    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.mobile = userEntity.getMobile();
        this.setCreatedBy(userEntity.getCreatedBy());
        this.setCreatedDate(userEntity.getCreatedDate());
        this.setLastModifiedBy(userEntity.getLastModifiedBy());
        this.setLastModifiedDate(userEntity.getLastModifiedDate());
    }
}
