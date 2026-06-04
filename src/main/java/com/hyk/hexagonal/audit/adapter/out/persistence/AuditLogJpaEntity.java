package com.hyk.hexagonal.audit.adapter.out.persistence;

import com.hyk.hexagonal.audit.domain.AuditAction;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "audit_logs")
class AuditLogJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private AuditAction action;

  @Column(name = "member_id", nullable = false)
  private long memberId;

  @Column(length = 1024)
  private String detail;

  @Column(name = "occurred_at", nullable = false)
  private Instant occurredAt;

  protected AuditLogJpaEntity() {
  }

  AuditLogJpaEntity(Long id, AuditAction action, long memberId, String detail, Instant occurredAt) {
    this.id = id;
    this.action = action;
    this.memberId = memberId;
    this.detail = detail;
    this.occurredAt = occurredAt;
  }

  Long getId() {
    return id;
  }

  AuditAction getAction() {
    return action;
  }

  long getMemberId() {
    return memberId;
  }

  String getDetail() {
    return detail;
  }

  Instant getOccurredAt() {
    return occurredAt;
  }
}
