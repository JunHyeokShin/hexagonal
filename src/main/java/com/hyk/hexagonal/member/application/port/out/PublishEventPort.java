package com.hyk.hexagonal.member.application.port.out;

public interface PublishEventPort {

  void publish(Object event);
}
