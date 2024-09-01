package com.shoppingcart.user.service.user.impl;

import com.shoppingcart.user.dto.JwtResponse;
import com.shoppingcart.user.dto.LoginRequest;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.SignUpRequest;
import com.shoppingcart.user.service.user.UserService;

//@Service
public class UserDemoService implements UserService {

    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        final JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        return jwtResponse;
    }

    @Override
    public JwtResponse signUp(SignUpRequest signUpRequest) {
        final JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwt("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c");
        return jwtResponse;
    }

    @Override
    public boolean recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest) {
        return true;
    }
}
