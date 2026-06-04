package com.hyk.hexagonal.audit.adapter.out.persistence;

import com.hyk.hexagonal.audit.application.port.out.LoadAuditLogsPort;
import com.hyk.hexagonal.audit.application.port.out.SaveAuditLogPort;
import com.hyk.hexagonal.audit.domain.AuditLog;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
class AuditLogPersistenceAdapter implements SaveAuditLogPort, LoadAuditLogsPort {

  private final SpringDataAuditLogRepository repository;
  private final AuditLogPersistenceMapper mapper;

  AuditLogPersistenceAdapter(
      SpringDataAuditLogRepository repository,
      AuditLogPersistenceMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public void save(AuditLog log) {
    repository.save(mapper.toJpaEntity(log));
  }

  @Override
  public List<AuditLog> findAll() {
    return repository.findAll().stream()
        .map(mapper::toDomain)
        .toList();
  }
}
