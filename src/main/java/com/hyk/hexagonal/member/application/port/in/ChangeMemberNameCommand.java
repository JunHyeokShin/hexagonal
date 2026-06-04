package com.hyk.hexagonal.member.application.port.in;

import com.hyk.hexagonal.member.domain.MemberId;

public record ChangeMemberNameCommand(MemberId memberId, String newName) {

  public ChangeMemberNameCommand {
    if (memberId == null) {
      throw new IllegalArgumentException("memberId must not be null");
    }
    if (newName == null || newName.isBlank()) {
      throw new IllegalArgumentException("newName must not be blank");
    }
  }
}
