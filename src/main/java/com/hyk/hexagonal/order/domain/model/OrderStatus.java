package com.hyk.hexagonal.order.domain.model;

/** 주문 상태. */
public enum OrderStatus {
  /** 접수됨(이벤트 발행 직후 기본 상태). */
  PLACED,
  /** 재고 확보 등 후속 처리가 성공해 확정됨. */
  CONFIRMED,
  /** 재고 부족 등으로 거절됨. */
  REJECTED
}
