# Changelog

[Index](Index.md)

## 0.1.2 — 2026-09-02

- Migrated the rule model to conditions/actions: `Rule` with `id`, `label`, `conditions` and `actions`.
- Made conditions modular: a condition tests an employee variable (`seniority`) against a value using a comparison operator.
- Changed the evaluation to be **cumulative**: every matching rule adds its `additionalDays` to the base allowance.
- Flattened the seniority bonus values in the configuration (15/20/25 years now grant 1 day).

## 0.1.1 — 2026-09-02

- Added the `Employee` entity and vacation-days computation from base allowance plus seniority bonus.
- Configured `exec-maven-plugin` in `pom.xml` so the application runs with a single `mvnw.cmd compile exec:java` command.
- Fixed a null-pointer error by creating the employee after loading the configuration.

## 0.1.0 — 2026-09-01

- Initial project structure: Maven build, `config/rules.json` and `RulesConfig` model.
- Base documentation wiki.
