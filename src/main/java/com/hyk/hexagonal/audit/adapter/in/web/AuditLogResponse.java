package com.hyk.hexagonal.audit.adapter.in.web;

import com.hyk.hexagonal.audit.domain.AuditAction;
import com.hyk.hexagonal.audit.domain.AuditLog;
import java.time.Instant;

record AuditLogResponse(
    long id,
    AuditAction action,
    long memberId,
    String detail,
    Instant occurredAt) {

  static AuditLogResponse from(AuditLog log) {
    return new AuditLogResponse(
        log.id(),
        log.action(),
        log.memberId(),
        log.detail(),
        log.occurredAt());
  }
}
