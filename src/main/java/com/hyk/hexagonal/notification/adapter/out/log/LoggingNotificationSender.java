package com.hyk.hexagonal.notification.adapter.out.log;

import com.hyk.hexagonal.notification.domain.model.Notification;
import com.hyk.hexagonal.notification.domain.port.out.NotificationSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 로그 기반 알림 전송 아웃바운드 어댑터.
 *
 * <p>실제 서비스라면 이메일/SMS/푸시 어댑터로 교체되며, 포트만 동일하면 도메인/애플리케이션은 변경되지 않는다.
 */
@Slf4j
@Component
class LoggingNotificationSender implements NotificationSender {

  @Override
  public void send(Notification notification) {
    log.info("[notification:{}] {}", notification.channel(), notification.message());
  }
}
