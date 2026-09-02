# HR Rules Engine

Java 21 HR rules engine. Computes vacation days from a base allowance plus seniority-based extras. Rules are data-driven (`config/rules.json`): changing a parameter requires no code change.

## Prerequisites

- JDK 21 or newer
- None: Maven does not need to be installed, the `mvnw.cmd` wrapper takes care of it (local download on first run).

## Structure

```
├── config/rules.json          # rules (base days + seniority)
├── docs/                      # documentation
├── src/main/java/com/hr/rulesengine/
│   └── Main.java              # entry point (all logic, prototype)
└── pom.xml
```

## Commands

```sh
mvnw.cmd compile             # compile
mvnw.cmd test                # run tests
mvnw.cmd compile exec:java   # compile and run the program
```

## Current rules

- 25 base vacation days
- Seniority → extra days:

| Seniority | Extra days |
| --- | --- |
| 10 years | 1 |
| 15 years | 2 |
| 20 years | 3 |
| 25 years | 4 |

Full documentation is in [`docs/`](docs/Index.md).
