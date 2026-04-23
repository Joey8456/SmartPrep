package com.BTA.SmartPrep.domain.dto;

public record SolutionRequestDto(
        String codeString,
        long problemId,
        String userId,
        int categoryId

) {
}
