package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.dto.QuestionnaireSubmitRequest;

public interface QuestionnaireService {
    void submitQuestionnaire(QuestionnaireSubmitRequest request);
}