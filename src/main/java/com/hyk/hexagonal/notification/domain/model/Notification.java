package com.hyk.hexagonal.notification.domain.model;

/**
 * 알림 도메인 모델.
 *
 * <p>채널(주문/재고 등)과 메시지로 구성된 단순 값 객체다.
 */
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
