package com.hyk.hexagonal.audit.adapter.out.persistence;

import com.hyk.hexagonal.audit.domain.AuditLog;
import org.springframework.stereotype.Component;

@Component
class AuditLogPersistenceMapper {

  AuditLogJpaEntity toJpaEntity(AuditLog log) {
    return new AuditLogJpaEntity(
        log.id(),
        log.action(),
        log.memberId(),
        log.detail(),
        log.occurredAt());
  }

  AuditLog toDomain(AuditLogJpaEntity entity) {
    return AuditLog.rehydrate(
        entity.getId(),
        entity.getAction(),
        entity.getMemberId(),
        entity.getDetail(),
        entity.getOccurredAt());
  }
}
