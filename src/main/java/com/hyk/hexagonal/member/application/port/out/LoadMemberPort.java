package com.hyk.hexagonal.member.application.port.out;

import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import java.util.Optional;

public interface LoadMemberPort {

  Optional<Member> load(MemberId id);
}
