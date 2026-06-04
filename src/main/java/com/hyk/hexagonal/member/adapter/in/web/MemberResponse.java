package com.hyk.hexagonal.member.adapter.in.web;

import com.hyk.hexagonal.member.domain.Member;
import java.time.Instant;

record MemberResponse(long id, String email, String name, Instant createdAt) {

  static MemberResponse from(Member member) {
    return new MemberResponse(
        member.id().value(),
        member.email().value(),
        member.name(),
        member.createdAt());
  }
}
