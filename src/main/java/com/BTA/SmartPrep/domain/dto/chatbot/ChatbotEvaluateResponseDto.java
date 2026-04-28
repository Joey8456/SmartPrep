package com.BTA.SmartPrep.domain.dto.chatbot;

public record ChatbotEvaluateResponseDto(
        int score,
        String rating,
        String feedback
) {}