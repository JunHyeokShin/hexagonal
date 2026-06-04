package com.hyk.hexagonal.notification.application.service;

import com.hyk.hexagonal.notification.application.port.in.SendWelcomeCommand;
import com.hyk.hexagonal.notification.application.port.in.SendWelcomeUseCase;
import com.hyk.hexagonal.notification.application.port.out.NotificationGatewayPort;
import com.hyk.hexagonal.notification.domain.WelcomeNotification;
import org.springframework.stereotype.Service;

@Service
class SendWelcomeService implements SendWelcomeUseCase {

  private final NotificationGatewayPort gateway;

  SendWelcomeService(NotificationGatewayPort gateway) {
    this.gateway = gateway;
  }

  @Override
  public void send(SendWelcomeCommand command) {
    WelcomeNotification notification = WelcomeNotification.greeting(
        command.memberId(),
        command.email(),
        command.name(),
        command.occurredAt());
    gateway.send(notification);
  }
}
