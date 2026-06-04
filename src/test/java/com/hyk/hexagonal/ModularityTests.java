package com.hyk.hexagonal;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

  @Test
  void verifyModuleBoundaries() {
    ApplicationModules.of(HexagonalApplication.class).verify();
  }
}
