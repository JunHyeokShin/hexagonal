package com.hyk.hexagonal.notification.adapter.out.notification;

import com.hyk.hexagonal.notification.application.port.out.NotificationGatewayPort;
import com.hyk.hexagonal.notification.domain.WelcomeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
class LoggingNotificationGateway implements NotificationGatewayPort {

  private static final Logger log = LoggerFactory.getLogger(LoggingNotificationGateway.class);

  @Override
  public void send(WelcomeNotification notification) {
    log.info("[Notification] memberId={}, to={}, message=\"{}\", sentAt={}",
        notification.memberId(),
        notification.recipientEmail(),
        notification.message(),
        notification.sentAt());
  }
}
