package com.BTA.SmartPrep.domain.dto.chatbot;

public record ChatbotEvaluateRequestDto(
        String userId,
        int categoryId,
        String question,
        String difficulty,
        String answer
) {}