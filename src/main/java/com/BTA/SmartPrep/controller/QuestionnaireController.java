package com.BTA.SmartPrep.controller;

import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.domain.entity.Question;
import com.BTA.SmartPrep.dto.QuestionnaireResultDTO;
import com.BTA.SmartPrep.dto.QuestionnaireSubmissionDTO;
import com.BTA.SmartPrep.service.QuestionnaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/questionnaire")
public class QuestionnaireController {

    private final QuestionnaireService questionnaireService;

    public QuestionnaireController(QuestionnaireService questionnaireService) {
        this.questionnaireService = questionnaireService;
    }

    /**
     * GET /api/questionnaire/questions
     *
     * Returns all questionnaire questions from the DB.
     * Called when a new user reaches the intro questionnaire screen.
     */
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getQuestions() {
        return ResponseEntity.ok(questionnaireService.getQuestions());
    }

    /**
     * POST /api/questionnaire/submit
     *
     * Accepts the user's answers, scores them, saves proficiency data,
     * and returns the recommended first problem + proficiency breakdown.
     *
     * Request body: QuestionnaireSubmissionDTO
     * {
     *   "userId": "...",
     *   "answers": {
     *     "<questionId>": "A",
     *     "<questionId>": "C",
     *     ...
     *   }
     * }
     */
    @PostMapping("/submit")
    public ResponseEntity<QuestionnaireResultDTO> submitQuestionnaire(
            @RequestBody QuestionnaireSubmissionDTO submission) {
        QuestionnaireResultDTO result = questionnaireService.processAndRecommend(submission);
        return ResponseEntity.ok(result);
    }

    /**
     * GET /api/questionnaire/recommend/{userId}
     *
     * For users who have already completed the questionnaire.
     * Returns the next recommended problem based on their current proficiency scores.
     */
    @GetMapping("/recommend/{userId}")
    public ResponseEntity<Problem> getNextRecommendation(@PathVariable UUID userId) {
        Problem recommended = questionnaireService.recommendNextProblem(userId);
        return ResponseEntity.ok(recommended);
    }
}
