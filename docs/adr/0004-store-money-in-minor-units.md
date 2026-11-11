# ADR-0004: Store money in minor units

## Status

Accepted

## Context

Floating-point arithmetic cannot represent many decimal values exactly. Financial calculations also need an explicit currency and predictable overflow and rounding behavior.

## Decision

FINCORE represents money as a signed 64-bit integer containing ISO 4217 minor units together with a currency. For example, USD 10.25 is stored as `1025` and `USD`. Basic addition and subtraction require equal currencies and use exact arithmetic to detect overflow.

Rounding is permitted only in named operations, such as fee calculation or currency conversion, with an explicitly selected rounding mode. Persistence will use `BIGINT` for the amount and a three-character currency code.

## Consequences

- Addition and subtraction are deterministic.
- Floating-point errors are eliminated.
- Currency mismatch becomes an explicit error.
- Fee multiplication and conversion need dedicated policies.
- Currency metadata must account for currencies with different minor-unit scales.

## Alternatives considered

- `double`: rejected because binary floating point is unsafe for financial values.
- Unconstrained `BigDecimal`: rejected as the primary representation because scale and rounding can vary unless every operation applies strict policies.

