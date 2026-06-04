package com.hyk.hexagonal.notification.adapter.in.event;

import com.hyk.hexagonal.member.domain.event.MemberRegistered;
import com.hyk.hexagonal.notification.application.port.in.SendWelcomeCommand;
import com.hyk.hexagonal.notification.application.port.in.SendWelcomeUseCase;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
class MemberRegisteredEventListener {

  private final SendWelcomeUseCase sendWelcomeUseCase;

  MemberRegisteredEventListener(SendWelcomeUseCase sendWelcomeUseCase) {
    this.sendWelcomeUseCase = sendWelcomeUseCase;
  }

  @ApplicationModuleListener
  void on(MemberRegistered event) {
    sendWelcomeUseCase.send(new SendWelcomeCommand(
        event.memberId(),
        event.email(),
        event.name(),
        event.occurredAt()));
  }
}
