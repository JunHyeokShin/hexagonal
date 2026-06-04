package com.hyk.hexagonal.member.domain;

import java.time.Instant;
import java.util.Objects;

public final class Member {

  private final MemberId id;
  private final Email email;
  private final String name;
  private final Instant createdAt;

  private Member(MemberId id, Email email, String name, Instant createdAt) {
    this.id = id;
    this.email = Objects.requireNonNull(email, "email must not be null");
    this.name = requireNonBlank(name);
    this.createdAt = Objects.requireNonNull(createdAt, "createdAt must not be null");
  }

  public static Member register(Email email, String name, Instant now) {
    return new Member(null, email, name, now);
  }

  public static Member rehydrate(MemberId id, Email email, String name, Instant createdAt) {
    Objects.requireNonNull(id, "id must not be null when rehydrating");
    return new Member(id, email, name, createdAt);
  }

  public Member changeName(String newName) {
    return new Member(this.id, this.email, newName, this.createdAt);
  }

  public boolean isPersisted() {
    return id != null;
  }

  public MemberId id() {
    return id;
  }

  public Email email() {
    return email;
  }

  public String name() {
    return name;
  }

  public Instant createdAt() {
    return createdAt;
  }

  private static String requireNonBlank(String value) {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("name must not be blank");
    }
    return value;
  }
}
