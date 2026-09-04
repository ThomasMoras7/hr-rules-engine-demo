[Index](Index.md) > [API](API.md) > Main

# Main

## Overview

Single-class prototype. Contains the application entry point, the `Employee` entity, the rule-loading logic and the data model. It reads `config/rules.json` and deserializes it into `RulesConfig`.

## Data

`RulesConfig` is defined here as a `record`, mirroring the JSON content (see [DataStructure](DataStructure.md)):

| Field | Type | Description |
| --- | --- | --- |
| `baseVacationDays` | integer | Base vacation days |
| `rules` | list of `Rule` | Vacation bonus rules (conditions/actions) |

## Handlers

| Method | Description |
| --- | --- |
| `main(String[] args)` | Reads `config/rules.json`, builds an `Employee`, computes and prints its vacation days |
| `Employee.computeVacationDays(RulesConfig)` | Evaluates each rule and accumulates the extra days |
| `Employee.conditionMet(Condition)` | Tests a single condition against a variable and operator |
| `Employee` | Nested entity holding an employee's seniority and computed vacation days |
| `RulesConfig` | Nested immutable model for the rule set (`Rule`, `Condition`, `Action`) |

## Flow

On startup `main` reads the file `config/rules.json` and deserializes it into `RulesConfig`. It then creates an `Employee` with a fixed seniority (10 years), computes its vacation days, and prints the result.

Computing vacation days iterates over each rule: a rule applies when all of its conditions hold (implicit AND), and every applying rule cumulates its `additionalDays` on top of the base allowance. A failure to read or parse the file is reported on standard error and the program exits.

## Error Handling

- File unreadable, missing, or malformed JSON is caught and reported; no partial configuration is produced.
- An unknown condition `variable` or `operator` raises a runtime error when that rule is evaluated.

## Rules

The JSON document must match the `RulesConfig` shape. See [DataStructure](DataStructure.md) for the condition/action schema.
