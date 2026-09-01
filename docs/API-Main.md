[Index](Index.md) > [API](API.md) > Main

# Main

## Overview

Single-class prototype. Contains the application entry point, the rule-loading logic and the data model. It reads `config/rules.json` and deserializes it into `RulesConfig`.

## Data

`RulesConfig` is defined here as a `record`, mirroring the JSON content (see [DataStructure](DataStructure.md)):

| Field | Type | Description |
| --- | --- | --- |
| `baseVacationDays` | integer | Base vacation days |
| `seniorityRules` | list of `SeniorityRule` | Ordered seniority bonus rules |

## Handlers

| Method | Description |
| --- | --- |
| `main(String[] args)` | Reads `config/rules.json` and parses it into `RulesConfig` |
| `RulesConfig` | Nested immutable model for the rule set |

## Flow

On startup `main` reads the file `config/rules.json` and deserializes it into `RulesConfig`. A failure to read or parse the file is reported on standard error and the program exits.

## Error Handling

File unreadable, missing, or malformed JSON is caught and reported; no partial configuration is produced.

## Rules

The JSON document must match the `RulesConfig` shape.
