package com.shoppingcart.user.service.notification.impl;

import com.shoppingcart.user.dto.UserDto;
import com.shoppingcart.user.service.notification.UserNotificationService;
import org.springframework.stereotype.Service;

@Service
public class ActiveMQUserNotificationService implements UserNotificationService {



    @Override
    public boolean sendRecoveryPasswordNotification(UserDto user) {
        return true;
    }
}
