package com.BTA.SmartPrep.domain.dto;

public record ChatbotEvaluateResponseDto(
        int score,
        String rating,
        String feedback
) {}