package com.hyk.hexagonal.notification.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;
import com.hyk.hexagonal.notification.application.port.out.NotificationSender;
import com.hyk.hexagonal.notification.domain.model.Notification;

@RequiredArgsConstructor
@Service
class NotificationService implements SendNotificationUseCase {

  private final NotificationSender sender;

  @Override
  public void send(SendNotificationCommand command) {
    this.sender.send(new Notification(command.channel(), command.message()));
  }

}
