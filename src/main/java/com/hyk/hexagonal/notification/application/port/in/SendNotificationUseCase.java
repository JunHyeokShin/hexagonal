package com.hyk.hexagonal.notification.application.port.in;

public interface SendNotificationUseCase {

  void send(SendNotificationCommand command);

}
