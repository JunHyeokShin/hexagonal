package com.hyk.hexagonal;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Spring Modulith 모듈 구조 검증 테스트.
 *
 * <p>ApplicationContext 를 띄우지 않고 바이트코드 분석만으로 모듈 경계를 검증하므로 DB가 필요 없다.
 * 순환 의존, 허용되지 않은 내부 패키지 접근(NamedInterface 위반) 등을 잡아낸다.
 */
class ModularityTests {

  static final ApplicationModules modules = ApplicationModules.of(HexagonalApplication.class);

  @Test
  void verifiesModularStructure() {
    modules.verify();
  }

  @Test
  void printsModuleStructure() {
    modules.forEach(System.out::println);
  }

  /** build/spring-modulith-docs 아래에 PlantUML/AsciiDoc 모듈 문서를 생성한다. */
  @Test
  void writesDocumentationSnippets() {
    new Documenter(modules).writeDocumentation().writeIndividualModulesAsPlantUml();
  }
}
