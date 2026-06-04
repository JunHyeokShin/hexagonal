package com.hyk.hexagonal.member.domain.event;

import java.time.Instant;

public record MemberRegistered(
    long memberId,
    String email,
    String name,
    Instant occurredAt) {
}
