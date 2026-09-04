package com.hr.rulesengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/*
    Rules engine computing vacation days from a base allowance plus seniority-based extras.
    Rules are data-driven (`config/rules.json`): changing a parameter requires no code change.
 */
public final class Main {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /*
        Entry point (all logic: creates an employee, computes vacation days, prints the result).
    */
    public static void main() {
        try {
            RulesConfig config = OBJECT_MAPPER.readValue(Files.readAllBytes(Path.of("config/rules.json")), RulesConfig.class);

            Employee employee1 = new Employee(10);
            employee1.computeVacationDays(config);
            System.out.println("Jours de congé pour Employee 1: " + employee1.vacationDays);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
    }

    /*
        Employee entity holding an employee's seniority and computed vacation days.
    */
    static final class Employee {
        
        // seniority: employee's seniority (years)
        public int seniority;
        // vacationDays: computed vacation days
        public int vacationDays;


        /*
            Employee constructor.
            @param seniority: employee's seniority (years)
            @return: none
        */
        Employee(int seniority) {
            this.seniority = seniority;
        }

        /*
            Applies the rules to compute the employee's vacation days.
            @param config: rule set
            @return: none
        */
        void computeVacationDays(RulesConfig config) {
            int additionalVacationDays = 0;
            for (RulesConfig.Rule rule : config.rules) {
                boolean conditionsMet = rule.conditions.stream().allMatch(this::conditionMet);
                if (conditionsMet) {
                    additionalVacationDays += rule.actions.stream().mapToInt(action -> action.additionalDays).sum();
                }
            }
            vacationDays = config.baseVacationDays + additionalVacationDays;
        }

        /*
            Tests a single condition against the employee state.
            @param condition: condition to test
            @return: true if the condition holds, false otherwise
        */
        boolean conditionMet(RulesConfig.Condition condition) {
            int actual = switch (condition.variable()) {
                case "seniority" -> seniority;
                default -> throw new IllegalArgumentException("Unknown variable: " + condition.variable());
            };
            return switch (condition.operator()) {
                case ">=" -> actual >= condition.value();
                case ">" -> actual > condition.value();
                case "<=" -> actual <= condition.value();
                case "<" -> actual < condition.value();
                case "==" -> actual == condition.value();
                case "!=" -> actual != condition.value();
                default -> throw new IllegalArgumentException("Unknown operator: " + condition.operator());
            };
        }
    }

    /*
        Rules configuration model mirroring 'config/rules.json'.
        @param baseVacationDays: base vacation days
        @param rules: vacation bonus rules (conditions/actions
    */
    public record RulesConfig(int baseVacationDays, List<Rule> rules) {

        /*
            Rule model, granted when all its conditions hold.
            @param id: stable identifier of the rule
            @param label: human-readable description of the rule
            @param conditions: list of conditions; all must hold for the rule to apply
            @param actions: list of effects applied when the rule applies
        */
        public record Rule(int id, String label, List<Condition> conditions, List<Action> actions) {
        }

        /*
            Condition model, testing a single employee variable against a value.
            @param variable: employee variable being tested (e.g. 'seniority')
            @param operator: comparison operator applied to the variable
            @param value: threshold the variable is compared against
        */
        public record Condition(String variable, String operator, int value) {
        }

        /*
            Action model, adding vacation days to the employee's total.
            @param additionalDays: extra vacation days granted

            PLANNED: support multiple actions concerning other employee variables.
        */
        public record Action(int additionalDays) {
        }
    }
}
