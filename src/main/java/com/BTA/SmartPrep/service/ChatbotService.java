package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.ChatbotEvaluateRequestDto;
import com.BTA.SmartPrep.domain.dto.ChatbotEvaluateResponseDto;

public interface ChatbotService {
    ChatbotEvaluateResponseDto evaluate(ChatbotEvaluateRequestDto request);
}