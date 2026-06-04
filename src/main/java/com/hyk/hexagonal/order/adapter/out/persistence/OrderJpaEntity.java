package com.hyk.hexagonal.order.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 JPA 엔티티(영속성 모델).
 *
 * <p>도메인 모델과 분리된 영속성 전용 클래스다. 상태는 도메인 enum 대신 문자열로 저장하여
 * 영속성 모델이 도메인에 의존하지 않도록 한다(변환은 {@link OrderMapper} 담당).
 * 패키지 외부로 노출하지 않기 위해 package-private 으로 둔다.
 */
@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class OrderJpaEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false, length = 20)
  private String status;

  @Column(nullable = false)
  private Instant placedAt;
}
