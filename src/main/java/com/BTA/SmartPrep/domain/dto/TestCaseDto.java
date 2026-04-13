package com.BTA.SmartPrep.domain.dto;

public record TestCaseDto(
        String testId,
        String testCase,
        String expectedOutput,
        String problem
) {
}
