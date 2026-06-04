package com.hyk.hexagonal.notification.application.port.in;

public interface SendWelcomeUseCase {

  void send(SendWelcomeCommand command);
}
