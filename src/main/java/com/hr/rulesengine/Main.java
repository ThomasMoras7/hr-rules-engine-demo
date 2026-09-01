package com.hr.rulesengine;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public final class Main {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        try {
            RulesConfig config = OBJECT_MAPPER.readValue(Files.readAllBytes(Path.of("config/rules.json")), RulesConfig.class);
        } catch (IOException e) {
            System.err.println("Failed to load config: " + e.getMessage());
        }
    }

    public record RulesConfig(int baseVacationDays, List<SeniorityRule> seniorityRules) {

        public record SeniorityRule(int minYears, int additionalDays) {
        }
    }
}
