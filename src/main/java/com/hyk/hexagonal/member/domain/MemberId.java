package com.hyk.hexagonal.member.domain;

public record MemberId(long value) {

  public MemberId {
    if (value <= 0) {
      throw new IllegalArgumentException("MemberId must be positive: " + value);
    }
  }
}
