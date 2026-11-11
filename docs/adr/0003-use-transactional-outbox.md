# ADR-0003: Use a transactional outbox

## Status

Accepted

## Context

FINCORE must update financial state and publish integration events without losing an event or publishing an event for a rolled-back transaction. A database commit and a Kafka publish cannot form one simple local transaction.

## Decision

Each business transaction that publishes an integration event will write the business state and an outbox record in the same PostgreSQL transaction. A background publisher sends pending outbox records to Kafka and records delivery progress.

Consumers must be idempotent and track processed event identifiers. Publishing and consuming are therefore at-least-once operations.

## Consequences

- A committed business change cannot silently lose its event.
- Duplicate delivery remains possible and must be handled.
- Events are eventually, not immediately, visible to consumers.
- The outbox requires monitoring, retry policy, cleanup, and dead-letter handling.

## Alternatives considered

- Publish directly to Kafka inside the request: rejected because a process crash can leave database state and messaging state inconsistent.
- Distributed two-phase commit: rejected because of operational complexity and limited ecosystem support.

