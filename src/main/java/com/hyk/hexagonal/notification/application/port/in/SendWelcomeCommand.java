package com.hyk.hexagonal.notification.application.port.in;

import java.time.Instant;

public record SendWelcomeCommand(
    long memberId,
    String email,
    String name,
    Instant occurredAt) {

  public SendWelcomeCommand {
    if (email == null || email.isBlank()) {
      throw new IllegalArgumentException("email must not be blank");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    if (occurredAt == null) {
      throw new IllegalArgumentException("occurredAt must not be null");
    }
  }
}
