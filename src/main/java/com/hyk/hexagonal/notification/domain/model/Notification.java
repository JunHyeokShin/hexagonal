package com.hyk.hexagonal.notification.domain.model;

public record Notification(String channel, String message) {

  public Notification {
    if (channel == null || channel.isBlank()) {
      throw new IllegalArgumentException("알림 채널은 필수입니다.");
    }
    if (message == null || message.isBlank()) {
      throw new IllegalArgumentException("알림 메시지는 필수입니다.");
    }
  }

}
