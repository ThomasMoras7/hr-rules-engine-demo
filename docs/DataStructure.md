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
| `seniorityRules` | array | Ordered list of seniority bonus rules |

### seniorityRules[]

Each element:

| Field | Type | Description |
| --- | --- | --- |
| `minYears` | integer | Minimum years of seniority required |
| `additionalDays` | integer | Extra vacation days granted from `minYears` |

Rules are evaluated in increasing `minYears` order; the applicable bonus is the one with the highest `minYears` not exceeding the employee's seniority.

### Current values

| `minYears` | `additionalDays` |
| --- | --- |
| 10 | 1 |
| 15 | 2 |
| 20 | 3 |
| 25 | 4 |

Base vacation days: 25.

## Notes

- Missing or unknown JSON fields are ignored by the loader; unknown enum-like values cause a parse failure.
- The model is a Java `record`, therefore immutable.
