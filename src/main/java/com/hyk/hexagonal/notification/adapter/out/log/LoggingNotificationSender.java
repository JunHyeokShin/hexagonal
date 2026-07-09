package com.hyk.hexagonal.notification.adapter.out.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.hyk.hexagonal.notification.application.port.out.NotificationSender;
import com.hyk.hexagonal.notification.domain.model.Notification;

@Slf4j
@Component
class LoggingNotificationSender implements NotificationSender {

  @Override
  public void send(Notification notification) {
    log.info("[notification:{}] {}", notification.channel(), notification.message());
  }

}
