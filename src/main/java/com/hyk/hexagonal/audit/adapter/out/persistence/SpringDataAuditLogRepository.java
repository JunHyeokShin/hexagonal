package com.hyk.hexagonal.audit.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataAuditLogRepository extends JpaRepository<AuditLogJpaEntity, Long> {
}
