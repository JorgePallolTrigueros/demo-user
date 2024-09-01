package com.shoppingcart.user.service.notification.impl;

import com.shoppingcart.user.dto.UserDto;
import com.shoppingcart.user.service.notification.UserNotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ActiveMQUserNotificationService implements UserNotificationService {

    private final JmsTemplate jmsTemplate;
    private final String destination;

    public ActiveMQUserNotificationService(JmsTemplate jmsTemplate,@Value("password-recovery-queue") String destination) {
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
    }

    @Override
    public boolean sendRecoveryPasswordNotification(UserDto user) {
        try {
            log.info("Enviando usuario para recuperacion de contraseña: {}",user);
            jmsTemplate.convertAndSend(destination, user);
        } catch (Exception e) {
            log.error("Ocurrio un error al intentar enviar notificacion",e);
            return false;
        }
        return true;
    }
}
