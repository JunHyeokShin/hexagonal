package com.hyk.hexagonal.audit.application.service;

import com.hyk.hexagonal.audit.application.port.in.RecordAuditCommand;
import com.hyk.hexagonal.audit.application.port.in.RecordAuditUseCase;
import com.hyk.hexagonal.audit.application.port.out.SaveAuditLogPort;
import com.hyk.hexagonal.audit.domain.AuditLog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class RecordAuditService implements RecordAuditUseCase {

  private final SaveAuditLogPort saveAuditLogPort;

  RecordAuditService(SaveAuditLogPort saveAuditLogPort) {
    this.saveAuditLogPort = saveAuditLogPort;
  }

  @Override
  public void record(RecordAuditCommand command) {
    AuditLog log = AuditLog.record(
        command.action(),
        command.memberId(),
        command.detail(),
        command.occurredAt());
    saveAuditLogPort.save(log);
  }
}
