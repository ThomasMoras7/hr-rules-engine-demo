# Optimisations

[Index](Index.md)

Improvement tracker. Categories: `OPT` (performance), `FAC` (DRY), `EXT` (config), `SIM` (lightweight).

- `FAC` — Extract the model (`RulesConfig`, `Rule`, `Condition`, `Action`) and the rule-loading logic out of `Main` into separate files.
- `EXT` — Generalize the condition evaluation beyond `seniority`: resolve any employee field by name so new variables require only config, not code.
- `EXT` — Support compound condition logic (explicit OR, negation, grouping) in the rule model, beyond the current implicit AND between conditions.
- `OPT` — Cache or index the rules at load time if rule evaluation becomes a bottleneck with many rules.
