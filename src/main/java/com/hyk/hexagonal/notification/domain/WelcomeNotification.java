package com.hyk.hexagonal.notification.domain;

import java.time.Instant;
import java.util.Objects;

public record WelcomeNotification(
    long memberId,
    String recipientEmail,
    String message,
    Instant sentAt) {

  public WelcomeNotification {
    Objects.requireNonNull(recipientEmail, "recipientEmail must not be null");
    Objects.requireNonNull(message, "message must not be null");
    Objects.requireNonNull(sentAt, "sentAt must not be null");
  }

  public static WelcomeNotification greeting(long memberId, String email, String name, Instant sentAt) {
    return new WelcomeNotification(memberId, email, "Welcome, " + name + "!", sentAt);
  }
}
