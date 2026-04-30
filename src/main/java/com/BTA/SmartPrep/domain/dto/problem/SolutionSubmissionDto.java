package com.BTA.SmartPrep.domain.dto.problem;

import com.BTA.SmartPrep.domain.entity.TestCase;

import java.util.List;

public record SolutionSubmissionDto(
        String color,
        int passed,
        int total,
        float score,
        List<TestCase> failedCases,
        String error,
        double runTimeMs,
        String runTimeMessage
) {

}
