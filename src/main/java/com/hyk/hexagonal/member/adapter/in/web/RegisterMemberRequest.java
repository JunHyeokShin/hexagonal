package com.hyk.hexagonal.member.adapter.in.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

record RegisterMemberRequest(
    @NotBlank @Email String email,
    @NotBlank String name) {
}
