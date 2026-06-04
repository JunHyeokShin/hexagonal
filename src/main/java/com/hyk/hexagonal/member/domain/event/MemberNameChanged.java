package com.hyk.hexagonal.member.domain.event;

import java.time.Instant;

public record MemberNameChanged(
    long memberId,
    String oldName,
    String newName,
    Instant occurredAt) {
}
