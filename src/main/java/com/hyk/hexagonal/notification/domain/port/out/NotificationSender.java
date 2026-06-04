package com.hyk.hexagonal.notification.domain.port.out;

import com.hyk.hexagonal.notification.domain.model.Notification;

/**
 * 알림 전송 아웃바운드 포트.
 *
 * <p>실제 전송 채널(콘솔/이메일/SMS 등)을 추상화한다. 본 예제에서는 로그 어댑터로 구현한다.
 */
public interface NotificationSender {

  void send(Notification notification);
}
