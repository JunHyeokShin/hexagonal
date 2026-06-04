package com.hyk.hexagonal.member.adapter.in.web;

import com.hyk.hexagonal.member.application.port.in.ChangeMemberNameCommand;
import com.hyk.hexagonal.member.application.port.in.ChangeMemberNameUseCase;
import com.hyk.hexagonal.member.application.port.in.DeleteMemberUseCase;
import com.hyk.hexagonal.member.application.port.in.FindMemberUseCase;
import com.hyk.hexagonal.member.application.port.in.RegisterMemberCommand;
import com.hyk.hexagonal.member.application.port.in.RegisterMemberUseCase;
import com.hyk.hexagonal.member.domain.Email;
import com.hyk.hexagonal.member.domain.Member;
import com.hyk.hexagonal.member.domain.MemberId;
import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
class MemberController {

  private final RegisterMemberUseCase registerMemberUseCase;
  private final FindMemberUseCase findMemberUseCase;
  private final ChangeMemberNameUseCase changeMemberNameUseCase;
  private final DeleteMemberUseCase deleteMemberUseCase;

  MemberController(
      RegisterMemberUseCase registerMemberUseCase,
      FindMemberUseCase findMemberUseCase,
      ChangeMemberNameUseCase changeMemberNameUseCase,
      DeleteMemberUseCase deleteMemberUseCase) {
    this.registerMemberUseCase = registerMemberUseCase;
    this.findMemberUseCase = findMemberUseCase;
    this.changeMemberNameUseCase = changeMemberNameUseCase;
    this.deleteMemberUseCase = deleteMemberUseCase;
  }

  @PostMapping
  ResponseEntity<Void> register(@Valid @RequestBody RegisterMemberRequest request) {
    MemberId id = registerMemberUseCase.register(
        new RegisterMemberCommand(new Email(request.email()), request.name()));
    return ResponseEntity.created(URI.create("/api/members/" + id.value())).build();
  }

  @GetMapping("/{id}")
  MemberResponse find(@PathVariable long id) {
    Member member = findMemberUseCase.findById(new MemberId(id));
    return MemberResponse.from(member);
  }

  @PatchMapping("/{id}/name")
  ResponseEntity<Void> changeName(
      @PathVariable long id,
      @Valid @RequestBody ChangeMemberNameRequest request) {
    changeMemberNameUseCase.changeName(
        new ChangeMemberNameCommand(new MemberId(id), request.name()));
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable long id) {
    deleteMemberUseCase.delete(new MemberId(id));
    return ResponseEntity.noContent().build();
  }
}
