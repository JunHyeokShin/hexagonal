package com.hyk.hexagonal.audit.application.port.in;

import com.hyk.hexagonal.audit.domain.AuditLog;
import java.util.List;

public interface ListAuditLogsUseCase {

  List<AuditLog> listAll();
}
