package com.shoppingcart.user.service.user;

import com.shoppingcart.user.dto.JwtResponse;
import com.shoppingcart.user.dto.LoginRequest;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.SignUpRequest;

public interface UserService {

    JwtResponse login(LoginRequest loginRequest);

    JwtResponse signUp(SignUpRequest signUpRequest);
}
