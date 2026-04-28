package com.BTA.SmartPrep.domain.dto.problem;

public record TestCaseDto(
        int testId,
        int problemId,
        String input_args,
        String expected_output,
        int is_hidden
) {
}
