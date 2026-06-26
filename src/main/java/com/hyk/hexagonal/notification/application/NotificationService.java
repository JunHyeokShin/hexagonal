package com.hyk.hexagonal.notification.application;

import com.hyk.hexagonal.notification.domain.model.Notification;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationCommand;
import com.hyk.hexagonal.notification.application.port.in.SendNotificationUseCase;
import com.hyk.hexagonal.notification.application.port.out.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/** 알림 유스케이스 구현(애플리케이션 계층). */
@Service
@RequiredArgsConstructor
public class NotificationService implements SendNotificationUseCase {

  private final NotificationSender sender;

  @Override
  public void send(SendNotificationCommand command) {
    sender.send(new Notification(command.channel(), command.message()));
  }
}
