package com.hyk.hexagonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 애플리케이션 진입점.
 *
 * <p>{@link EnableAsync} 는 Spring Modulith 의 {@code @ApplicationModuleListener}
 * (비동기 + 트랜잭션 분리 + 이벤트 발행 기록) 가 별도 스레드에서 동작하도록 활성화한다.
 */
@EnableAsync
@SpringBootApplication
public class HexagonalApplication {

  public static void main(String[] args) {
    SpringApplication.run(HexagonalApplication.class, args);
  }

}
