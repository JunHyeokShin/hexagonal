package com.hyk.hexagonal.member.application.port.in;

import com.hyk.hexagonal.member.domain.Email;

public record RegisterMemberCommand(Email email, String name) {

  public RegisterMemberCommand {
    if (email == null) {
      throw new IllegalArgumentException("email must not be null");
    }
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name must not be blank");
    }
  }

}
