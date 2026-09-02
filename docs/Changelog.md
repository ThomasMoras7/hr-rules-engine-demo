# Changelog

[Index](Index.md)

## 0.1.1 — 2026-09-02

- Added the `Employee` entity and vacation-days computation from base allowance plus seniority bonus.
- Configured `exec-maven-plugin` in `pom.xml` so the application runs with a single `mvnw.cmd compile exec:java` command.
- Fixed a null-pointer error by creating the employee after loading the configuration.

## 0.1.0 — 2026-09-01

- Initial project structure: Maven build, `config/rules.json`, `RulesConfig` model and `RulesConfigLoader`.
- Base documentation wiki.
