package com.shoppingcart.user.service.impl;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import com.shoppingcart.user.model.JwtResponse;
import com.shoppingcart.user.model.LoginRequest;
import com.shoppingcart.user.model.SignUpRequest;
import com.shoppingcart.user.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserJpaService  implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserJpaService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }


    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse signUp(SignUpRequest signUpRequest) {

        UserEntity userEntity = new UserEntity();
        userEntity.setName(signUpRequest.getName());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setAddress(signUpRequest.getAddress());
        userEntity.setPhoneNumber(signUpRequest.getPhoneNumber());
        userEntity.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));//TODO encry ,  NO 123456
        userEntity.setCreatedAt(new Date());
        userEntity = this.userRepository.saveAndFlush(userEntity);

        final JwtResponse response = new JwtResponse();
        response.setJwt("creado");//TODO generar jwt

        return response;
    }
}
