package com.hyk.hexagonal.notification.application.port.out;

import com.hyk.hexagonal.notification.domain.WelcomeNotification;

public interface NotificationGatewayPort {

  void send(WelcomeNotification notification);
}
