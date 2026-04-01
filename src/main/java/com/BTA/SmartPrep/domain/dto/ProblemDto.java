package com.BTA.SmartPrep.domain.dto;

import com.BTA.SmartPrep.domain.entity.ProblemDifficulty;

public record ProblemDto (
        String problemId,
        String title,
        String prompt,
        String examples,
        ProblemDifficulty problemDifficulty,
        int category
) {
}
