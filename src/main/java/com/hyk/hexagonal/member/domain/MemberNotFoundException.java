package com.hyk.hexagonal.member.domain;

public class MemberNotFoundException extends RuntimeException {

  public MemberNotFoundException(MemberId id) {
    super("Member not found: " + id.value());
  }
}
