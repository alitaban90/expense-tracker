package com.snapppay.tasks.expensetracker.security.dtos;

import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import lombok.Data;

@Data
public class UserDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;

    public UserDto() {
    }

    public UserDto(UserEntity userEntity) {
        this.id = userEntity.getId();
        this.username = userEntity.getUsername();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.mobile = userEntity.getMobile();
    }
}
