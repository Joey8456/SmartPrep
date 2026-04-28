package com.BTA.SmartPrep.domain.dto.problem;

public record SolutionRequestDto(
        String codeString,
        long problemId,
        String userId,
        int categoryId
) {
}
