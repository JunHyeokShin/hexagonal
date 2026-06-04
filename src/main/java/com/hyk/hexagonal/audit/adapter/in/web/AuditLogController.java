package com.hyk.hexagonal.audit.adapter.in.web;

import com.hyk.hexagonal.audit.application.port.in.ListAuditLogsUseCase;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController {

  private final ListAuditLogsUseCase listAuditLogsUseCase;

  AuditLogController(ListAuditLogsUseCase listAuditLogsUseCase) {
    this.listAuditLogsUseCase = listAuditLogsUseCase;
  }

  @GetMapping
  List<AuditLogResponse> list() {
    return listAuditLogsUseCase.listAll().stream()
        .map(AuditLogResponse::from)
        .toList();
  }
}
