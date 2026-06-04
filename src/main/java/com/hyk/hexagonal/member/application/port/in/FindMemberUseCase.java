package com.hyk.hexagonal.member.application.port.in;

import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;

public interface FindMemberUseCase {

  Member findById(MemberId id);
}
