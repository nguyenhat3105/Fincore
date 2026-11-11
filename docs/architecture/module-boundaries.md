# Module boundaries

| Module | Owns | Expected public contracts/events |
|---|---|---|
| Identity | users, credentials, roles | identity lookup, user status events |
| Wallet | wallet lifecycle and balance projections | wallet use cases, wallet events |
| Ledger | accounts, journals and postings | posting use case, ledger events |
| Payment | authorization, capture, void and refund | payment use cases and lifecycle events |
| Merchant | merchant profiles and credentials | merchant lookup and status events |
| Settlement | batches, items and payout orchestration | settlement use cases and events |
| Reconciliation | runs, comparisons and discrepancies | reconciliation use cases and discrepancy events |
| Risk | rules and risk decisions | transaction assessment port |
| Webhook | endpoints, signatures and deliveries | webhook scheduling port |
| Audit | immutable audit records | audit recording port |

Data ownership follows module ownership. Sharing a PostgreSQL instance does not permit one module to modify another module's tables directly. Cross-module changes are coordinated through application ports during a local workflow or through events for asynchronous workflows.

Public contracts belong to `application.port.in`, command/query packages, or an explicit published-event package. Domain internals, persistence mappings, and adapters remain internal.

