package com.hyk.hexagonal.member.domain.event;

import java.time.Instant;

public record MemberDeleted(
    long memberId,
    Instant occurredAt) {
}
