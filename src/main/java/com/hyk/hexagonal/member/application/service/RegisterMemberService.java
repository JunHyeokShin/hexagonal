package com.hyk.hexagonal.member.application.service;

import com.hyk.hexagonal.member.application.port.in.RegisterMemberCommand;
import com.hyk.hexagonal.member.application.port.in.RegisterMemberUseCase;
import com.hyk.hexagonal.member.application.port.out.PublishEventPort;
import com.hyk.hexagonal.member.application.port.out.SaveMemberPort;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import com.hyk.hexagonal.member.domain.event.MemberRegistered;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class RegisterMemberService implements RegisterMemberUseCase {

  private final SaveMemberPort saveMemberPort;
  private final PublishEventPort publishEventPort;
  private final Clock clock;

  RegisterMemberService(
      SaveMemberPort saveMemberPort,
      PublishEventPort publishEventPort,
      Clock clock) {
    this.saveMemberPort = saveMemberPort;
    this.publishEventPort = publishEventPort;
    this.clock = clock;
  }

  @Override
  public MemberId register(RegisterMemberCommand command) {
    Instant now = Instant.now(clock);
    Member newMember = Member.register(command.email(), command.name(), now);
    MemberId savedId = saveMemberPort.save(newMember);
    publishEventPort.publish(new MemberRegistered(
        savedId.value(), command.email().value(), command.name(), now));
    return savedId;
  }
}
