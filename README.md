# FINCORE

FINCORE is a portfolio-grade digital wallet and payment settlement platform. The project is being built as a Java 21 modular monolith with explicit domain boundaries and production-oriented engineering practices.

## Current status

The Day 2 foundation is available: the Spring Boot application, system and health endpoints, baseline security, modular boundaries, shared domain primitives, architecture tests, formatting and coverage reporting.

## Technology stack

- Java 21
- Spring Boot 3
- Gradle
- Spring Web, Validation, Security and Actuator
- JUnit 5, MockMvc and JaCoCo
- ArchUnit
- Spotless

## Prerequisites

- JDK 21
- Git
- Docker Desktop will be required from Day 3

## Run locally

```powershell
.\gradlew.bat clean build
.\gradlew.bat bootRun
```

The application starts on `http://localhost:8080` by default. Override the port with `FINCORE_SERVER_PORT`.

## Run verification

```powershell
.\gradlew.bat clean check
.\gradlew.bat test jacocoTestReport
```

Coverage reports are written to `build/reports/jacoco/test/html/index.html`.

## CI/CD

Every push and pull request runs formatting checks, compilation, unit tests, ArchUnit rules, coverage generation and JAR packaging. Successful `main` and `v*` builds publish a non-root container image to GitHub Container Registry after independently verifying the release candidate.

See the [Git workflow](docs/development/git-workflow.md) for branch, commit and pull-request conventions.

## Available endpoints

| Method | Endpoint | Public | Purpose |
|---|---|---:|---|
| GET | `/api/v1/system/info` | Yes | Safe application information |
| GET | `/actuator/health` | Yes | Application health |
| GET | `/actuator/info` | Yes | Actuator application information |

All other endpoints require authentication. JWT authentication will be introduced with the identity module.

## Project structure

```text
src/main/java/com/nguyencongnhat/fincore/
|-- FincoreApplication.java
|-- system/
|   |-- api/
|   `-- application/
`-- shared/
    |-- api/
    |-- config/
    |-- domain/
    `-- observability/
```

Controllers delegate to application services. Domain packages must not depend on API or infrastructure packages. Business modules will be introduced incrementally with ArchUnit enforcement.

The bounded contexts are Identity, Wallet, Ledger, Payment, Merchant, Settlement, Reconciliation, Risk, Webhook and Audit. Their responsibilities and dependency rules are documented in [architecture overview](docs/architecture/overview.md) and [module boundaries](docs/architecture/module-boundaries.md).

Architectural decisions are recorded in the [ADR index](docs/adr/README.md).

## Development conventions

- Store configuration in environment variables; never commit secrets.
- Use immutable API response records.
- Keep business logic out of controllers.
- Run `spotlessApply` before committing when formatting changes are needed.
- Prefer small, behavior-focused tests.

## Roadmap

1. Foundation and project bootstrap
2. Identity, OAuth2/JWT and RBAC
3. Wallet and double-entry ledger
4. P2P transfers and payment lifecycle
5. Kafka outbox, settlement and reconciliation
6. Reliability, performance and AWS deployment

