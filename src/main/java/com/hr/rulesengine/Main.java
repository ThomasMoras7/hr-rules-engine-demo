package com.hr.rulesengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Main {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        try {
            RulesConfig config = OBJECT_MAPPER.readValue(Files.readAllBytes(Path.of("config/rules.json")), RulesConfig.class);

            Employee employee1 = new Employee(10);
            employee1.computeVacationDays(config);
            System.out.println("Jours de congé pour Employee 1: " + employee1.vacationDays);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
    }

    static final class Employee {
        public int seniority;
        public int vacationDays;

        Employee(int seniority) {
            this.seniority = seniority;
        }

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

    public record RulesConfig(int baseVacationDays, List<Rule> rules) {

        public record Rule(int id, String label, List<Condition> conditions, List<Action> actions) {
        }

        public record Condition(String variable, String operator, int value) {
        }

        public record Action(int additionalDays) {
        }
    }
}
