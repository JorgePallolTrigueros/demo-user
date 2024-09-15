package com.shoppingcart.user.service.password;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.UserDto;
import com.shoppingcart.user.service.notification.UserNotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordRecoverService {

    public static Map<String, UserDto> tokenUserMap = new HashMap<>();
    private final UserNotificationService userNotificationService;
    private final UserRepository userRepository;

    public boolean recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest){
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

    public void verifyTokenExpiryEveryMinute(){

    }

    public void changePassword(String email, String token,String password,String password2){

    }

}
