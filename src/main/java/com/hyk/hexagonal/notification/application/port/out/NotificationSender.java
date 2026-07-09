package com.hyk.hexagonal.notification.application.port.out;

import com.hyk.hexagonal.notification.domain.model.Notification;

public interface NotificationSender {

  void send(Notification notification);

}
