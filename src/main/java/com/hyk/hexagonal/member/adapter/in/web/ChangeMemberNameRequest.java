package com.hyk.hexagonal.member.adapter.in.web;

import jakarta.validation.constraints.NotBlank;

record ChangeMemberNameRequest(@NotBlank String name) {
}
