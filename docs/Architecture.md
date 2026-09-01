# Architecture

[Index](Index.md)

## Overview

HR Rules Engine is a Java 21 prototype that evaluates HR leave rules, starting with vacation days computed from a base allowance plus seniority-based additions. Rules are data-driven: they live in `config/rules.json` and are loaded at runtime, so policy changes do not require code changes.

## Layout

```
.
├── config/
│   └── rules.json          # Data-driven rules (editable without code change)
├── docs/                   # Project documentation (this wiki)
├── src/
│   └── main/java/com/hr/rulesengine/
│       └── Main.java       # Entry point, rule loading and data model (single class)
└── pom.xml                 # Maven build definition
```

## Design principles

- **Data-driven rules**: business rules are configuration, not code. The engine reads `config/rules.json`.
- **Prototype simplicity**: all logic lives in a single class, `Main`.
- **Minimal dependencies**: only Jackson (JSON binding) and JUnit (tests).

## Build

Maven is not required to be pre-installed: use the wrapper `mvnw.cmd` (Windows) which downloads a local Maven on first run.

```sh
mvnw.cmd compile    # compile
mvnw.cmd test       # run tests
```

## The `target/` directory

`target/` is Maven's build output directory. It is **generated** — everything inside is produced by the build and can be safely deleted (and is typically regenerated on the next build). It should not be committed to version control.

Current contents after `compile`:

| Path | Purpose |
| --- | --- |
| `target/classes/` | Compiled `.class` files, mirroring the package structure (`com/hr/rulesengine/Main.class`, plus the generated nested record classes `Main$RulesConfig…`). This is the runtime classpath root. |
| `target/generated-sources/` | Generated sources (currently empty; reserved by annotations processing). |
| `target/maven-status/` | Internal Maven bookkeeping (last compilation input/output hashes) used for incremental builds. |

Phases add more entries as the build proceeds:

- `test` adds `target/test-classes/` (compiled tests) and `target/surefire-reports/` (reports + results).
- `package` produces the distributable artifact, e.g. `target/rules-engine-0.1.0.jar` (a JAR embedding the compiled classes; for an executable runnable JAR with dependencies, the `maven-shade-plugin` would be needed).

The artifact — the JAR — is the final deliverable produced by Maven: a single file containing the compiled application, ready to run or distribute. `mvnw.cmd clean` deletes the whole `target/` directory.
