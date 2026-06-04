package com.hyk.hexagonal.member.application.port.in;

import com.hyk.hexagonal.member.domain.MemberId;

public interface RegisterMemberUseCase {

  MemberId register(RegisterMemberCommand command);
}
