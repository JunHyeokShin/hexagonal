package com.hyk.hexagonal.member.application.service;

import com.hyk.hexagonal.member.application.port.in.DeleteMemberUseCase;
import com.hyk.hexagonal.member.application.port.out.DeleteMemberPort;
import com.hyk.hexagonal.member.application.port.out.LoadMemberPort;
import com.hyk.hexagonal.member.application.port.out.PublishEventPort;
import com.hyk.hexagonal.member.domain.MemberId;
import com.hyk.hexagonal.member.domain.MemberNotFoundException;
import com.hyk.hexagonal.member.domain.event.MemberDeleted;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class DeleteMemberService implements DeleteMemberUseCase {

  private final LoadMemberPort loadMemberPort;
  private final DeleteMemberPort deleteMemberPort;
  private final PublishEventPort publishEventPort;
  private final Clock clock;

  DeleteMemberService(
      LoadMemberPort loadMemberPort,
      DeleteMemberPort deleteMemberPort,
      PublishEventPort publishEventPort,
      Clock clock) {
    this.loadMemberPort = loadMemberPort;
    this.deleteMemberPort = deleteMemberPort;
    this.publishEventPort = publishEventPort;
    this.clock = clock;
  }

  @Override
  public void delete(MemberId id) {
    if (loadMemberPort.load(id).isEmpty()) {
      throw new MemberNotFoundException(id);
    }
    deleteMemberPort.delete(id);
    publishEventPort.publish(new MemberDeleted(id.value(), Instant.now(clock)));
  }
}
