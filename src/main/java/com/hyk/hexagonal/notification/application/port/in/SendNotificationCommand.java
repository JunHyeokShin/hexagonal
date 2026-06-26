package com.hyk.hexagonal.notification.application.port.in;

/** 알림 발송 커맨드. */
public record SendNotificationCommand(String channel, String message) {}
