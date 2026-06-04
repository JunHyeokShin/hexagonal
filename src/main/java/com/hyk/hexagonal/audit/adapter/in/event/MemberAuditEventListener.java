package com.hyk.hexagonal.audit.adapter.in.event;

import com.hyk.hexagonal.audit.application.port.in.RecordAuditCommand;
import com.hyk.hexagonal.audit.application.port.in.RecordAuditUseCase;
import com.hyk.hexagonal.audit.domain.AuditAction;
import com.hyk.hexagonal.member.domain.event.MemberDeleted;
import com.hyk.hexagonal.member.domain.event.MemberNameChanged;
import com.hyk.hexagonal.member.domain.event.MemberRegistered;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
class MemberAuditEventListener {

  private final RecordAuditUseCase recordAuditUseCase;

  MemberAuditEventListener(RecordAuditUseCase recordAuditUseCase) {
    this.recordAuditUseCase = recordAuditUseCase;
  }

  @ApplicationModuleListener
  void on(MemberRegistered event) {
    recordAuditUseCase.record(new RecordAuditCommand(
        AuditAction.MEMBER_REGISTERED,
        event.memberId(),
        "email=" + event.email() + ", name=" + event.name(),
        event.occurredAt()));
  }

  @ApplicationModuleListener
  void on(MemberNameChanged event) {
    recordAuditUseCase.record(new RecordAuditCommand(
        AuditAction.MEMBER_NAME_CHANGED,
        event.memberId(),
        "oldName=" + event.oldName() + ", newName=" + event.newName(),
        event.occurredAt()));
  }

  @ApplicationModuleListener
  void on(MemberDeleted event) {
    recordAuditUseCase.record(new RecordAuditCommand(
        AuditAction.MEMBER_DELETED,
        event.memberId(),
        null,
        event.occurredAt()));
  }
}
