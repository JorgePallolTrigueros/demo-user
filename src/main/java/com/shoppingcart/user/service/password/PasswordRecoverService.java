package com.shoppingcart.user.service.password;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.UserDto;
import com.shoppingcart.user.service.notification.UserNotificationService;
import com.shoppingcart.user.util.AppUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PasswordRecoverService {

    public static final int MAX_MIN_TO_WAIT_PASSWORD_CHANGED = 5;
    public static Map<String, UserDto> tokenUserMap = new HashMap<>();
    // 1234 -> User(jorge,jorge@gmail.com,1234,.......)
    // 4567 -> User(Jon,jon@gmail.com,4567...)
    //.....

    private final UserNotificationService userNotificationService;
    private final UserRepository userRepository;

    public boolean tokenIsValid(String token){
        return tokenUserMap.containsKey(token);
    }

    public boolean recoverPassword(RecoveryPasswordRequest recoveryPasswordRequest){
        final Optional<UserEntity> optionalUser = this.userRepository.findById(recoveryPasswordRequest.getEmail());
        if(optionalUser.isEmpty()){
            log.info("Usuario {} no encontrado en el metodo recovery password",recoveryPasswordRequest.getEmail());
            return false;
        }

        final UserEntity userEntity = optionalUser.get();

        final UserDto user = UserDto
                .builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .createdAt(userEntity.getCreatedAt())
                .tokenPasswordCreatedAt(new Date())
                .token(UUID.randomUUID().toString())
                .build();

        tokenUserMap.put(user.getToken(),user);

        return userNotificationService.sendRecoveryPasswordNotification(user);
    }

    // Que es un CRON: es una tarea programada con periodos de ejecucion
    // * en cualquier momento podemos trabajar numeros 0 - 60 segundos, minutos, 0 - 23 horas, 0 - 6 dias de semana, dias del mes 30,29,28 etc
    // 0/1 -> ejecutarse al momento cero cada minuto , */1 -> se va a ejecutar al momento cualquiera cada minuto
    // Segundos    ->  0
    // Minutos     -> */1
    // Horas       ->  *
    // Dias        ->  ? cualquier se usa para dias y semanas
    // Semanas     -> * cualquier
    // Meses       -> * cualquier mes

    @Scheduled(cron = "0 */1 * ? * *")
    public void verifyTokenExpiryEveryMinute(){
        log.info("Verificar expiracion de tokens");
        for(UserDto user: tokenUserMap.values()){
            long minutesPassed = AppUtil.minutesDiff(user.getTokenPasswordCreatedAt(),new Date());
            if(minutesPassed >= MAX_MIN_TO_WAIT_PASSWORD_CHANGED){
                log.info("Se ha removido el user: {}",user);
                tokenUserMap.remove(user.getToken());
            }
        }
    }

    public void changePassword(String token,String password,String password2){
        final boolean isTokenValid = tokenIsValid(token);
        if(!isTokenValid){
            log.info("No es un token valido");
            throw new RuntimeException("No es un token valido");
        }

        final UserDto userDto = tokenUserMap.get(token);
        final String email = userDto.getEmail();

        final Optional<UserEntity> optionalUser = this.userRepository.findById(email);

        if(optionalUser.isEmpty()){
            log.info("Usuario {} no encontrado en el metodo recovery password",email);
            throw new RuntimeException("No se ha encontrado el usuario con email "+email);
        }

        if(!password.equals(password2)){
            throw new RuntimeException("Las contraseñas deben ser iguales");
        }

        final UserEntity userEntity = optionalUser.get();

        userEntity.setPassword(password);

        this.userRepository.saveAndFlush(userEntity);

        final UserDto user = UserDto
                .builder()
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .createdAt(userEntity.getCreatedAt())
                .build();

        final boolean notificationSend = userNotificationService.sendPasswordChangedNotification(user);
        log.info("Notification was send: {}",notificationSend);
    }




}
