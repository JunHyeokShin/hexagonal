package com.hyk.hexagonal.member.adapter.out.persistence;

import com.hyk.hexagonal.member.domain.Email;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import org.springframework.stereotype.Component;

@Component
class MemberPersistenceMapper {

  MemberJpaEntity toJpaEntity(Member member) {
    Long id = member.isPersisted() ? member.id().value() : null;
    return new MemberJpaEntity(id, member.email().value(), member.name(), member.createdAt());
  }

  Member toDomain(MemberJpaEntity entity) {
    return Member.rehydrate(
        new MemberId(entity.getId()),
        new Email(entity.getEmail()),
        entity.getName(),
        entity.getCreatedAt());
  }
}
