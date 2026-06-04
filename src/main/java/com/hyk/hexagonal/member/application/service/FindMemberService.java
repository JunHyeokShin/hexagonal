package com.hyk.hexagonal.member.application.service;

import com.hyk.hexagonal.member.application.port.in.FindMemberUseCase;
import com.hyk.hexagonal.member.application.port.out.LoadMemberPort;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import com.hyk.hexagonal.member.domain.MemberNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class FindMemberService implements FindMemberUseCase {

  private final LoadMemberPort loadMemberPort;

  FindMemberService(LoadMemberPort loadMemberPort) {
    this.loadMemberPort = loadMemberPort;
  }

  @Override
  public Member findById(MemberId id) {
    return loadMemberPort.load(id).orElseThrow(() -> new MemberNotFoundException(id));
  }
}
