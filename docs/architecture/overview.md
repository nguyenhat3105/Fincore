# Architecture overview

FINCORE is a modular monolith. It is deployed as one Spring Boot application while preserving business boundaries in source code and automated architecture tests.

```text
HTTP request
    |
    v
+----------------+
| API            |  controllers and transport DTOs
+-------+--------+
        |
        v
+----------------+
| Application    |  use cases, commands, queries, transaction orchestration
+-------+--------+
        |
        v
+----------------+
| Domain         |  aggregates, value objects, domain events and invariants
+-------+--------+
        ^
        |
+-------+--------+
| Infrastructure |  persistence, messaging and external adapters
+----------------+
```

Dependencies point inward. The domain does not depend on Spring, HTTP, JPA, Kafka, Redis, or Jackson. Infrastructure implements outbound ports declared by the application or domain layers.

Cross-module interaction uses an inbound application port or a published event contract. A module must not use another module's repository or persistence entity.

