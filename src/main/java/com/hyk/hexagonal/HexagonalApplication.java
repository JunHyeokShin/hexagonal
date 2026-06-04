package com.hyk.hexagonal;

import java.time.Clock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class HexagonalApplication {

  static void main(String[] args) {
    SpringApplication.run(HexagonalApplication.class, args);
  }

  @Bean
  Clock clock() {
    return Clock.systemUTC();
  }

}
