package com.shoppingcart.user.service;

import com.shoppingcart.user.model.JwtResponse;
import com.shoppingcart.user.model.LoginRequest;
import com.shoppingcart.user.model.RecoveryPasswordRequest;
import com.shoppingcart.user.model.SignUpRequest;

public interface UserService {

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse signUp(SignUpRequest signUpRequest);

    boolean recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest);
}
