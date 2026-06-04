package com.hyk.hexagonal.member.application.port.out;

import com.hyk.hexagonal.member.domain.MemberId;

public interface DeleteMemberPort {

  void delete(MemberId id);
}
