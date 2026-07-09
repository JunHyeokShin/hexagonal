package com.hyk.hexagonal.notification.application.port.in;

public record SendNotificationCommand(String channel, String message) {

}
