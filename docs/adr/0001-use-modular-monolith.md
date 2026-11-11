# ADR-0001: Use a modular monolith

## Status

Accepted

## Context

FINCORE contains several financial bounded contexts, but it is developed and operated by a small team. Starting with independently deployed services would introduce distributed transactions, network failure modes, deployment coordination, and significantly higher infrastructure cost before those costs provide business value.

Financial commands also benefit from a single local ACID transaction while the product and domain boundaries are still evolving.

## Decision

FINCORE will start as one deployable Spring Boot application divided into explicit business modules. Each module owns its model and exposes behavior through inbound application ports or published events. ArchUnit tests enforce the dependency direction and prevent accidental access to another module's internals.

Modules may be extracted into services later when deployment independence, ownership, or scaling data provides a concrete reason.

## Consequences

- Local financial operations can use PostgreSQL transactions.
- Local development and deployment remain simple.
- Module discipline must be enforced in code review and automated tests.
- The application scales as one deployment until a module is extracted.
- Extraction remains possible because cross-module calls use explicit contracts.

## Alternatives considered

- Microservices: rejected initially because their operational and consistency costs outweigh current benefits.
- Unstructured monolith: rejected because shared entities and repositories would make later change unsafe.

