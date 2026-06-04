package com.hyk.hexagonal.audit.application.port.out;

import com.hyk.hexagonal.audit.domain.AuditLog;
import java.util.List;

public interface LoadAuditLogsPort {

  List<AuditLog> findAll();
}
