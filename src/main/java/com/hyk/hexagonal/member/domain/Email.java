package com.hyk.hexagonal.member.domain;

import java.util.regex.Pattern;

public record Email(String value) {

  private static final Pattern PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

  public Email {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("email must not be blank");
    }
    if (!PATTERN.matcher(value).matches()) {
      throw new IllegalArgumentException("invalid email format: " + value);
    }
  }
}
