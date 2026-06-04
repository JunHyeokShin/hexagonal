package com.hyk.hexagonal.audit.application.service;

import com.hyk.hexagonal.audit.application.port.in.ListAuditLogsUseCase;
import com.hyk.hexagonal.audit.application.port.out.LoadAuditLogsPort;
import com.hyk.hexagonal.audit.domain.AuditLog;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class ListAuditLogsService implements ListAuditLogsUseCase {

  private final LoadAuditLogsPort loadAuditLogsPort;

  ListAuditLogsService(LoadAuditLogsPort loadAuditLogsPort) {
    this.loadAuditLogsPort = loadAuditLogsPort;
  }

  @Override
  public List<AuditLog> listAll() {
    return loadAuditLogsPort.findAll();
  }
}
