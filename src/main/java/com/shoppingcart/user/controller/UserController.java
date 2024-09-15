package com.shoppingcart.user.controller;

import com.shoppingcart.user.api.LoginApi;
import com.shoppingcart.user.api.RecoveryPasswordApi;
import com.shoppingcart.user.api.SignUpApi;
import com.shoppingcart.user.dto.JwtResponse;
import com.shoppingcart.user.dto.LoginRequest;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.SignUpRequest;
import com.shoppingcart.user.service.password.PasswordRecoverService;
import com.shoppingcart.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class UserController implements LoginApi, SignUpApi, RecoveryPasswordApi {

    // 1. API
    // 2. Servicio
    // 3. Conexión con base

    private final UserService userService;
    private final PasswordRecoverService passwordRecoverService;


    @Override
    public Optional<NativeWebRequest> getRequest() {
        return LoginApi.super.getRequest();
    }


    @Override
    public ResponseEntity<Void> recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest) {
        final boolean recoverySent = passwordRecoverService.recoverPassword(recoveryPasswordRequest);
        if(recoverySent){
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @Override// v1/user/login
    public ResponseEntity<JwtResponse> login(LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }

    @Override
    public ResponseEntity<JwtResponse> signUp(SignUpRequest signUpRequest) {
        return ResponseEntity.ok(userService.signUp(signUpRequest));
    }
}
