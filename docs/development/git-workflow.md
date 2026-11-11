# Git workflow

FINCORE uses short-lived feature branches and a protected `main` branch.

## Branches

Create branches from the latest `main` using one of these prefixes:

```text
feature/FIN-<ticket>-<description>
fix/FIN-<ticket>-<description>
chore/FIN-<ticket>-<description>
```

Examples:

```text
feature/FIN-020-local-infrastructure
feature/FIN-031-wallet-aggregate
fix/FIN-044-idempotency-race
```

## Before pushing

Run the same verification used by CI:

```powershell
.\gradlew.bat clean check bootJar --no-daemon --console=plain
```

## Commit convention

Use focused conventional commits:

```text
feat: add wallet creation use case
fix: prevent duplicate transfer posting
test: cover concurrent balance updates
docs: record ledger consistency decision
build: add PostgreSQL test container
ci: publish container images to GHCR
```

Do not mix unrelated documentation, infrastructure, and business changes in one commit. Rebase or merge the latest `main` before opening a pull request.

## Pull requests

Every pull request must pass the CI workflow. Configure the GitHub repository to require the `Verify Java 21 build` check and at least one review before merge. Disable direct pushes to `main` once the repository has more than one contributor.

Merging to `main` publishes an immutable `sha-*` image and a moving `main` image to GitHub Container Registry. Tags such as `v0.1.0` additionally publish semantic-version image tags.

## Suggested incremental history

```text
chore: bootstrap FINCORE Spring Boot application
build: enforce modular architecture boundaries
docs: record initial architecture decisions
ci: add verification and container publishing pipelines
```

