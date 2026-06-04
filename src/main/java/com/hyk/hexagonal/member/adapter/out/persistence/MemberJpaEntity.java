package com.hyk.hexagonal.member.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "members")
class MemberJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String name;

  @Column(name = "created_at", nullable = false)
  private Instant createdAt;

  protected MemberJpaEntity() {
  }

  MemberJpaEntity(Long id, String email, String name, Instant createdAt) {
    this.id = id;
    this.email = email;
    this.name = name;
    this.createdAt = createdAt;
  }

  Long getId() {
    return id;
  }

  String getEmail() {
    return email;
  }

  String getName() {
    return name;
  }

  Instant getCreatedAt() {
    return createdAt;
  }
}
