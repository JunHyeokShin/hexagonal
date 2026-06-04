package com.hyk.hexagonal.member.adapter.out.persistence;

import com.hyk.hexagonal.member.application.port.out.DeleteMemberPort;
import com.hyk.hexagonal.member.application.port.out.LoadMemberPort;
import com.hyk.hexagonal.member.application.port.out.SaveMemberPort;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
class MemberPersistenceAdapter implements SaveMemberPort, LoadMemberPort, DeleteMemberPort {

  private final SpringDataMemberRepository repository;
  private final MemberPersistenceMapper mapper;

  MemberPersistenceAdapter(SpringDataMemberRepository repository, MemberPersistenceMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public MemberId save(Member member) {
    MemberJpaEntity saved = repository.save(mapper.toJpaEntity(member));
    return new MemberId(saved.getId());
  }

  @Override
  public Optional<Member> load(MemberId id) {
    return repository.findById(id.value()).map(mapper::toDomain);
  }

  @Override
  public void delete(MemberId id) {
    repository.deleteById(id.value());
  }
}
