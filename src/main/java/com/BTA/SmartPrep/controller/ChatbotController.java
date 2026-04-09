package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.dto.ChatbotEvaluateRequestDto;
import com.BTA.SmartPrep.domain.dto.ChatbotEvaluateResponseDto;
import com.BTA.SmartPrep.service.ChatbotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/chatbot")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatbotController {

    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/evaluate")
    public ResponseEntity<ChatbotEvaluateResponseDto> evaluate(
            @RequestBody ChatbotEvaluateRequestDto request
    ) {
        return ResponseEntity.ok(chatbotService.evaluate(request));
    }
}