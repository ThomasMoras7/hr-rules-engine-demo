# Features

[Index](Index.md)

- Load HR leave rules from a JSON configuration file (`config/rules.json`).
- Define a base number of vacation days.
- Model rules as conditions/actions: a rule applies when all its conditions hold, and applies its actions.
- Test conditions against employee variables (`seniority`) with comparison operators.
- Cumulate the extra days of every matching rule.
- Compute and print the vacation days of an employee on startup.
