package com.hyk.hexagonal.member.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataMemberRepository extends JpaRepository<MemberJpaEntity, Long> {
}
