package com.BTA.SmartPrep.dto;

import java.util.Map;
import java.util.UUID;

/**
 * Sent by the client when a user submits their questionnaire.
 *
 * answers: a map of { questionId -> selectedAnswer }
 *   e.g. { "3f2a...": "A", "7c1b...": "C" }
 */
public class QuestionnaireSubmissionDTO {

    private UUID userId;

    /**
     * Key   = questionId (UUID as String)
     * Value = selected answer: "A", "B", "C", or "D"
     */
    private Map<UUID, String> answers;

    public QuestionnaireSubmissionDTO() {}

    public QuestionnaireSubmissionDTO(UUID userId, Map<UUID, String> answers) {
        this.userId = userId;
        this.answers = answers;
    }

    public UUID getUserId() { return userId; }
    public void setUserId(UUID userId) { this.userId = userId; }

    public Map<UUID, String> getAnswers() { return answers; }
    public void setAnswers(Map<UUID, String> answers) { this.answers = answers; }
}
