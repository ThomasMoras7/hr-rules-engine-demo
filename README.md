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

## How it works

Rules live in `config/rules.json`. Each rule pairs **conditions** (tests on an employee variable, e.g. `seniority`) with **actions** (extra days granted). A rule applies when all its conditions hold, and the extra days of every matching rule are cumulated onto the base allowance.

```json
{
  "id": 1,
  "label": "Seniority of 10 years",
  "conditions": [ { "variable": "seniority", "operator": ">=", "value": 10 } ],
  "actions": [ { "additionalDays": 1 } ]
}
```

## Current rules

- 25 base vacation days
- Conditions support the `seniority` variable and operators `>=`, `>`, `<=`, `<`, `==`, `!=`.

| Label | Condition | Extra days |
| --- | --- | --- |
| Seniority of 10 years | seniority `>=` 10 | 1 |
| Seniority of 15 years | seniority `>=` 15 | 1 |
| Seniority of 20 years | seniority `>=` 20 | 1 |
| Seniority of 25 years | seniority `>=` 25 | 1 |

Full documentation is in [`docs/`](docs/Index.md).
