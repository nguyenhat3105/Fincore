# ADR-0002: Use PostgreSQL as the source of truth

## Status

Accepted

## Context

Wallet balances, ledger postings, payments, settlements, and reconciliation results require durable transactions, constraints, and traceable history. Redis and Kafka offer useful performance and messaging capabilities but do not replace the authoritative financial record.

## Decision

PostgreSQL will be the source of truth for financial state. Redis may hold caches, rate-limit counters, and short-lived coordination data. Kafka carries integration events. Neither Redis nor Kafka is authoritative for wallet balances or ledger history.

Database schema changes will be versioned with Flyway. Financial invariants will be protected in both domain logic and database constraints where practical.

## Consequences

- Financial state has one authoritative location.
- Transaction boundaries are explicit and auditable.
- Cache entries can be discarded and rebuilt.
- Database capacity and availability require careful operational management.
- Read scaling may later require projections or replicas.

## Alternatives considered

- Redis as the balance store: rejected because cache loss or asynchronous persistence is unacceptable for the authoritative balance.
- Event store as the initial source of truth: rejected because it increases implementation and operational complexity before replay requirements are proven.

