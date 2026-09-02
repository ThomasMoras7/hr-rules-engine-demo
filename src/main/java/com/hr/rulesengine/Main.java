package com.hr.rulesengine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class Main {

    static RulesConfig config = null;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static final class Employee {
        public int seniority;
        public int vacationDays;

        Employee() {
            seniority = 0;
            vacationDays = 0;
            computeVacationDays();
        }

        Employee(int seniority) {
            this.seniority = seniority;
            computeVacationDays();
        }

        void computeVacationDays() {
            int maxSeniority = 0;
            for (RulesConfig.SeniorityRule rule : config.seniorityRules) {
                if (seniority >= rule.minYears) {
                    maxSeniority = Math.max(maxSeniority, rule.minYears);
                    if (maxSeniority == rule.minYears) {
                        vacationDays = config.baseVacationDays + rule.additionalDays;
                    }
                }
            }
        }
    }
    
    static Employee employee1 = new Employee();

    public static void main(String[] args) {
        
        try {
            config = OBJECT_MAPPER.readValue(Files.readAllBytes(Path.of("config/rules.json")), RulesConfig.class);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }

        System.out.println("Jours de conge pour Employee 1: " + employee1.vacationDays);

    }

    public record RulesConfig(int baseVacationDays, List<SeniorityRule> seniorityRules) {

        public record SeniorityRule(int minYears, int additionalDays) {
        }
    }
}
