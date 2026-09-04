# DataStructure

[Index](Index.md)

## Overview

The application has a single data structure: `RulesConfig`, representing the rule set for computing leave days. It maps directly to `config/rules.json`.

## RulesConfig

File: `config/rules.json`
Java type: `com.hr.rulesengine.Main.RulesConfig`

| Field | Type | Description |
| --- | --- | --- |
| `baseVacationDays` | integer | Base number of vacation days for any employee |
| `rules` | array | Ordered list of vacation bonus rules |

### rules[]

Each element is a rule composed of **conditions** (what must hold) and **actions** (the effect when it holds):

| Field | Type | Description |
| --- | --- | --- |
| `id` | integer | Stable identifier of the rule |
| `label` | string | Human-readable description of the rule |
| `conditions` | array | List of conditions; all must hold for the rule to apply |
| `actions` | array | List of effects applied when the rule applies |

### conditions[]

A condition tests a single employee variable against a value. All conditions of a rule must be satisfied (implicit AND) for the rule to apply.

Supported variables: `seniority`.

Supported operators: `>=`, `>`, `<=`, `<`, `==`, `!=`.

| Field | Type | Description |
| --- | --- | --- |
| `variable` | string | Employee variable being tested (e.g. `seniority`) |
| `operator` | string | Comparison operator applied to the variable |
| `value` | integer | Threshold the variable is compared against |

### actions[]

Each action adds vacation days to the employee's total.

| Field | Type | Description |
| --- | --- | --- |
| `additionalDays` | integer | Extra vacation days granted |

## Evaluation

Rules are evaluated in order. Every rule whose conditions all hold contributes its actions: the extra days of all matching rules are **cumulated** (an employee with 25 years of seniority benefits from every seniority rule). The final total is `baseVacationDays` plus the sum of all matching rules' `additionalDays`.

### Current values

Base vacation days: 25.

| `id` | Label | Condition | `additionalDays` |
| --- | --- | --- | --- |
| 1 | Seniority of 10 years | seniority `>=` 10 | 1 |
| 2 | Seniority of 15 years | seniority `>=` 15 | 1 |
| 3 | Seniority of 20 years | seniority `>=` 20 | 1 |
| 4 | Seniority of 25 years | seniority `>=` 25 | 1 |

## Notes

- Missing or unknown JSON fields are ignored by the loader.
- An unknown `variable` or `operator` causes a runtime error at evaluation time; an unknown variable or operator in the JSON is reported when a rule is evaluated.
- The model is a Java `record`, therefore immutable.
