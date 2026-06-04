package com.hyk.hexagonal.audit.application.port.out;

import com.hyk.hexagonal.audit.domain.AuditLog;

public interface SaveAuditLogPort {

  void save(AuditLog log);
}
