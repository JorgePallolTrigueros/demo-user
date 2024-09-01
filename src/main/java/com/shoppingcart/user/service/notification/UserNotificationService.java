package com.shoppingcart.user.service.notification;

import com.shoppingcart.user.dto.UserDto;

public interface UserNotificationService {

    boolean sendRecoveryPasswordNotification(UserDto user);

}
