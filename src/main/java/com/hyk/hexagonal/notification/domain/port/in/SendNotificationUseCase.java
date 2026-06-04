package com.hyk.hexagonal.notification.domain.port.in;

/** 알림 발송 인바운드 포트(유스케이스). */
public interface SendNotificationUseCase {

  void send(SendNotificationCommand command);
}
