# Dependency rules

FINCORE uses the following package convention:

```text
<module>.api
<module>.application
<module>.application.port.in
<module>.application.port.out
<module>.domain
<module>.infrastructure
```

Rules enforced with ArchUnit:

1. Domain code does not depend on Spring, Jakarta Persistence, Jackson, API, application, or infrastructure packages.
2. Application code does not depend on API or infrastructure packages.
3. REST controllers reside in API packages and end with `Controller`.
4. Inbound ports are interfaces and end with `UseCase`.
5. Shared code does not depend on a business bounded context.
6. Top-level modules must not form dependency cycles.

Naming conventions for future code:

| Kind | Convention |
|---|---|
| REST adapter | `*Controller` |
| Inbound port | `*UseCase` |
| Outbound persistence port | `*Repository` |
| External outbound port | `*Gateway` or `*Publisher` |
| Request/response transport DTO | `*Request`, `*Response` |
| Use-case input | `*Command`, `*Query` |
| Published fact | `*Event` |
| Infrastructure implementation | `*Adapter` |

