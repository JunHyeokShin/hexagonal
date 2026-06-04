package com.hyk.hexagonal.audit.application.port.in;

import com.hyk.hexagonal.audit.domain.AuditAction;
import java.time.Instant;

public record RecordAuditCommand(
    AuditAction action,
    long memberId,
    String detail,
    Instant occurredAt) {

  public RecordAuditCommand {
    if (action == null) {
      throw new IllegalArgumentException("action must not be null");
    }
    if (occurredAt == null) {
      throw new IllegalArgumentException("occurredAt must not be null");
    }
  }
}
