# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build & Test Commands

Gradle wrapper. On Windows (PowerShell) use `gradlew.bat`; on Unix shells use `./gradlew`.

- Build: `gradlew.bat build`
- Run the app: `gradlew.bat bootRun`
- All tests: `gradlew.bat test`
- Single test: `gradlew.bat test --tests <FQCN>` (e.g., `com.hyk.hexagonal.ModularityTests`)
- Compile only (no DB needed): `gradlew.bat compileJava`
- Clean: `gradlew.bat clean`

**JAVA_HOME**: currently unset in the user's shell, so raw `gradlew.bat` will fail with "JAVA_HOME is not set". Temurin 25 is installed at `C:\Users\HYK\.jdks\temurin-25.0.3` (IntelliJ auto-installed). Either set `$env:JAVA_HOME` per-session or persist with `[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Users\HYK\.jdks\temurin-25.0.3", "User")`.

**MySQL**: `bootRun` and `@SpringBootTest` (e.g., `HexagonalApplicationTests.contextLoads`) need MySQL at `localhost:3306/hexagonal` with `root` / `1234`. DDL auto-generates via `ddl-auto: update`. `ModularityTests` is static analysis only — it does NOT need MySQL.

## Architecture

Spring Boot 4.0.6 / Java 25. **Strict hexagonal (ports & adapters) + clean architecture** organized as Spring Modulith modules under `com.hyk.hexagonal`. Currently three modules:

- `member` — REST CRUD for members (`/api/members`), publishes domain events
- `audit` — listens to all member events, persists `audit_logs` (`/api/audit-logs`)
- `notification` — listens to `MemberRegistered` only, sends welcome notifications via console logger

### Package layout per module (identical across all)

```
{module}/
├── domain/                     # framework-free java (no Spring/JPA/Lombok annotations)
├── application/
│   ├── port/in/                # one UseCase interface per use case + Command/Query records
│   ├── port/out/               # narrow interfaces (Save/Load/Delete/Publish — split per ISP)
│   └── service/                # @Service @Transactional, implements UseCase
└── adapter/
    ├── in/{web,event}/         # input adapters (REST, event listeners)
    └── out/{persistence,event,notification}/   # output adapters, implement port/out
```

Adapter and JPA entity classes are **package-private**; ports, use cases, domain types, and event records are **public**.

### Module boundaries

- **Modulith default**: only top-level public types are cross-module visible. All `{module}.domain.*`, `application.*`, `adapter.*` are internal.
- **Published API via `@NamedInterface`**: `member/domain/event/package-info.java` declares the `events` named interface. Other modules may import only `member.domain.event.*` (the event records) — nothing else from `member`.
- **Verification**: `ModularityTests.verifyModuleBoundaries` calls `ApplicationModules.of(HexagonalApplication.class).verify()`. Run it whenever module structure changes.

### Cross-module communication

Modules communicate **only via domain events**, never direct method calls:

- **Producer side**: services depend on `PublishEventPort` (an outgoing port). `SpringEventPublisherAdapter` wraps Spring's `ApplicationEventPublisher`. Services never inject `ApplicationEventPublisher` directly — that detail is an adapter concern.
- **Subscriber side**: `@ApplicationModuleListener` in `adapter/in/event/` (= `@TransactionalEventListener(AFTER_COMMIT) + @Async + @Transactional(REQUIRES_NEW)`). Listeners delegate to their own module's `UseCase` and never touch another module's domain types.
- **Event payloads are primitives only** (`long`, `String`, `Instant`). VOs like `MemberId` / `Email` do not leak across module boundaries.
- **Event Publication Registry** auto-enabled by `spring-modulith-starter-jpa`: an `event_publication` table is auto-created so unprocessed events survive restarts.

### Adding a new bounded context

1. Create a sibling top-level package under `com.hyk.hexagonal` (not a sub-package of an existing module).
2. Mirror the package layout above.
3. Keep `domain` framework-free; Spring annotations live in `application/service/` and `adapter/`.
4. To publish events, put them in `{module}/domain/event/` with `@NamedInterface("events")` in `package-info.java`.
5. To consume another module's events, import only its `domain.event.*`. Anything else is a boundary violation.
6. Run `gradlew.bat test --tests com.hyk.hexagonal.ModularityTests` to verify.

### Other conventions

- **`Clock` bean** is registered in `HexagonalApplication`; inject it wherever a service needs `Instant.now(...)`. Domain types take `Instant` directly, never `Clock` — keeps domain testable without mocking time.
- **`@EnableAsync`** on `HexagonalApplication` powers Modulith's async listeners.
- **Bean Validation** (`@Valid`, `@NotBlank`, `@Email`) annotates REST request records in `adapter/in/web/`. Domain VOs (`Email`, `MemberId`) re-validate in their compact constructors — defense in depth at the inner boundary.
- **Exception handling**: scope `@RestControllerAdvice(assignableTypes = SomeController.class)` to one controller, not the whole app, to keep modules isolated.
- **Lombok** is wired as `compileOnly` + `annotationProcessor`, but the current code avoids it in favor of Java 25 `record`s. Prefer records / explicit constructors over `@Data` to keep domain framework-free.
