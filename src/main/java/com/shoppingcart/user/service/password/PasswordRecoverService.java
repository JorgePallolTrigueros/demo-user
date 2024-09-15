package com.shoppingcart.user.service.password;

import com.shoppingcart.user.dao.entity.UserEntity;
import com.shoppingcart.user.dao.repository.UserRepository;
import com.shoppingcart.user.dto.RecoveryPasswordRequest;
import com.shoppingcart.user.dto.UserDto;
import com.shoppingcart.user.service.notification.UserNotificationService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

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

        tokenUserMap.forEach((token,user)->{
            //TODO comprobar que si ha pasado mas de 10 minutos borrar el token del mapa

        });
    }

    public void changePassword(String email, String token,String password,String password2){

    }

}
