package com.hyk.hexagonal.audit.domain;

import java.time.Instant;
import java.util.Objects;

public final class AuditLog {

  private final Long id;
  private final AuditAction action;
  private final long memberId;
  private final String detail;
  private final Instant occurredAt;

  private AuditLog(Long id, AuditAction action, long memberId, String detail, Instant occurredAt) {
    this.action = Objects.requireNonNull(action, "action must not be null");
    this.occurredAt = Objects.requireNonNull(occurredAt, "occurredAt must not be null");
    this.id = id;
    this.memberId = memberId;
    this.detail = detail;
  }

  public static AuditLog record(AuditAction action, long memberId, String detail, Instant occurredAt) {
    return new AuditLog(null, action, memberId, detail, occurredAt);
  }

  public static AuditLog rehydrate(long id, AuditAction action, long memberId, String detail, Instant occurredAt) {
    return new AuditLog(id, action, memberId, detail, occurredAt);
  }

  public Long id() {
    return id;
  }

  public AuditAction action() {
    return action;
  }

  public long memberId() {
    return memberId;
  }

  public String detail() {
    return detail;
  }

  public Instant occurredAt() {
    return occurredAt;
  }
}
