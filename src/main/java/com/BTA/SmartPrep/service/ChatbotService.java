package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.chatbot.ChatbotEvaluateRequestDto;
import com.BTA.SmartPrep.domain.dto.chatbot.ChatbotEvaluateResponseDto;

public interface ChatbotService {
    ChatbotEvaluateResponseDto evaluate(ChatbotEvaluateRequestDto request);
}