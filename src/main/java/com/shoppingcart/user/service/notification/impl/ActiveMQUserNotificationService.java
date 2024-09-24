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
    private final String passwordRecoveryQueue;
    private final String passwordChangedQueue;

    public ActiveMQUserNotificationService(JmsTemplate jmsTemplate,@Value("password-recovery-queue") String passwordRecoveryQueue,@Value("password-changed-queue") String passwordChangedQueue) {
        this.jmsTemplate = jmsTemplate;
        this.passwordRecoveryQueue = passwordRecoveryQueue;
        this.passwordChangedQueue = passwordChangedQueue;
    }

    @Override
    public boolean sendRecoveryPasswordNotification(UserDto user) {
        try {
            log.info("Enviando usuario para recuperacion de contraseña: {}",user);
            jmsTemplate.convertAndSend(passwordRecoveryQueue, user);
        } catch (Exception e) {
            log.error("Ocurrio un error al intentar enviar notificacion",e);
            return false;
        }
        return true;
    }

    @Override
    public boolean sendPasswordChangedNotification(UserDto user) {
        try {
            log.info("Enviando notification de contraseña cambiada: {}",user);
            jmsTemplate.convertAndSend(passwordChangedQueue, user);
        } catch (Exception e) {
            log.error("Ocurrio un error al intentar enviar notificacion",e);
            return false;
        }
        return true;
    }
}
