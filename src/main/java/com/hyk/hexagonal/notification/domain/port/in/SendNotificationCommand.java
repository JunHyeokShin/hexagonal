package com.hyk.hexagonal.notification.domain.port.in;

/** 알림 발송 커맨드. */
public record SendNotificationCommand(String channel, String message) {}
