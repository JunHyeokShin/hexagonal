package com.hyk.hexagonal.member.application.service;

import com.hyk.hexagonal.member.application.port.in.ChangeMemberNameCommand;
import com.hyk.hexagonal.member.application.port.in.ChangeMemberNameUseCase;
import com.hyk.hexagonal.member.application.port.out.LoadMemberPort;
import com.hyk.hexagonal.member.application.port.out.PublishEventPort;
import com.hyk.hexagonal.member.application.port.out.SaveMemberPort;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberNotFoundException;
import com.hyk.hexagonal.member.domain.event.MemberNameChanged;
import java.time.Clock;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class ChangeMemberNameService implements ChangeMemberNameUseCase {

  private final LoadMemberPort loadMemberPort;
  private final SaveMemberPort saveMemberPort;
  private final PublishEventPort publishEventPort;
  private final Clock clock;

  ChangeMemberNameService(
      LoadMemberPort loadMemberPort,
      SaveMemberPort saveMemberPort,
      PublishEventPort publishEventPort,
      Clock clock) {
    this.loadMemberPort = loadMemberPort;
    this.saveMemberPort = saveMemberPort;
    this.publishEventPort = publishEventPort;
    this.clock = clock;
  }

  @Override
  public void changeName(ChangeMemberNameCommand command) {
    Member member = loadMemberPort.load(command.memberId())
        .orElseThrow(() -> new MemberNotFoundException(command.memberId()));
    String oldName = member.name();
    Member renamed = member.changeName(command.newName());
    saveMemberPort.save(renamed);
    publishEventPort.publish(new MemberNameChanged(
        command.memberId().value(), oldName, command.newName(), Instant.now(clock)));
  }
}
