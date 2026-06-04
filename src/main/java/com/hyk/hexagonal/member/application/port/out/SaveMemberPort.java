package com.hyk.hexagonal.member.application.port.out;

import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;

public interface SaveMemberPort {

  MemberId save(Member member);
}
