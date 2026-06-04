package com.hyk.hexagonal.audit.application.port.in;

public interface RecordAuditUseCase {

  void record(RecordAuditCommand command);
}
