package com.BTA.SmartPrep.domain.dto.problem;

import com.BTA.SmartPrep.domain.entity.ProblemDifficulty;

public record ProblemDto (
        long problemId,
        String title,
        String prompt,
        String examples,
        ProblemDifficulty problemDifficulty,
        int category,
        String starterCode,
        String sampleTestCase,
        String methodName,
        String returnType,
        String parameterType,
        String sampleExpectedOutput
) {
}
