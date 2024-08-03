package com.snapppay.tasks.expensetracker.security.services;

import com.snapppay.tasks.expensetracker.security.dtos.UserDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserLoginDto;
import com.snapppay.tasks.expensetracker.security.dtos.UserRegisterDto;
import com.snapppay.tasks.expensetracker.security.entities.UserEntity;
import com.snapppay.tasks.expensetracker.security.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * The type Authentication service.
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * Instantiates a new Authentication service.
     *
     * @param userRepository        the user repository
     * @param passwordEncoder       the password encoder
     * @param authenticationManager the authentication manager
     */
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Register user dto.
     *
     * @param dto the dto
     * @return the user dto
     */
    public UserDto register(UserRegisterDto dto){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(dto.getUsername());
        userEntity.setPassword(passwordEncoder.encode(dto.getPassword()));
        userEntity.setEmail(dto.getEmail());
        userEntity.setFirstName(dto.getFirstName());
        userEntity.setLastName(dto.getLastName());
        userEntity.setMobile(dto.getMobile());
        userEntity = userRepository.save(userEntity);
        return new UserDto(userEntity);
    }

    /**
     * Authenticate user entity.
     *
     * @param dto the dto
     * @return the user entity
     */
    public UserEntity authenticate(UserLoginDto dto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));
        return userRepository.findByUsername(dto.getUsername()).orElseThrow();
    }
}
