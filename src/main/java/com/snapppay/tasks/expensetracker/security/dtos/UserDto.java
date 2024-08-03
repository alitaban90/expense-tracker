package com.snapppay.tasks.expensetracker.security.dtos;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The type User dto.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends AbstractAuditingDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
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
