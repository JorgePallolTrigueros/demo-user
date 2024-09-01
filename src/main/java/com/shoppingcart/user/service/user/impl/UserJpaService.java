package com.shoppingcart.user.service.user.impl;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import com.shoppingcart.user.dto.*;
import com.shoppingcart.user.service.notification.UserNotificationService;
import com.shoppingcart.user.service.user.JpaUserDetailService;
import com.shoppingcart.user.service.user.JwtService;
import com.shoppingcart.user.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserJpaService implements UserService {

    private final UserNotificationService userNotificationService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailService userDetailService;

    public UserJpaService(UserNotificationService userNotificationService, PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager, JpaUserDetailService userDetailService) {
        this.userNotificationService = userNotificationService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailService = userDetailService;
    }

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUser(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailService.loadUserByUsername(loginRequest.getUser());
        final String jwt = jwtService.generateToken(userDetails);

        return new JwtResponse(jwt);
    }

    @Override
    public JwtResponse signUp(SignUpRequest signUpRequest) {
        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.getName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setAddress(signUpRequest.getAddress());
        userEntity.setPhoneNumber(signUpRequest.getPhoneNumber());
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        userEntity.setCreatedAt(new Date());
        userRepository.saveAndFlush(userEntity);

        final UserDetails userDetails = userDetailService.loadUserByUsername(signUpRequest.getEmail());
        final String jwt = jwtService.generateToken(userDetails);

        return new JwtResponse(jwt);
    }

    @Override
    public boolean recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest) {
        final Optional<UserEntity> optionalUser = this.userRepository.findById(recoveryPasswordRequest.getEmail());
        if(optionalUser.isEmpty()){
            log.info("Usuario {} no encontrado en el metodo recovery password",recoveryPasswordRequest.getEmail());
            return false;
        }

        final UserEntity userEntity = optionalUser.get();

        return userNotificationService.sendRecoveryPasswordNotification(UserDto
                .builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .createdAt(userEntity.getCreatedAt())
                .build());
    }
}
