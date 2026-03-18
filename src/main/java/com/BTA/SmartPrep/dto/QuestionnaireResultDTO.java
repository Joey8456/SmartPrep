package com.BTA.SmartPrep.dto;

import com.BTA.SmartPrep.domain.entity.ProblemDifficulty;

import java.util.Map;
import java.util.UUID;

/**
 * Returned to the client after a questionnaire is scored.
 *
 * Contains:
 *  - proficiencyByCategory: the computed starting proficiency (0-100) per category
 *  - recommendedProblemId:  the first problem the user should attempt
 *  - recommendedDifficulty: the difficulty level mapped from their weakest category score
 *  - targetCategoryName:    the category the recommended problem belongs to
 */
public class QuestionnaireResultDTO {

    /**
     * Key   = category name (human-readable for the frontend)
     * Value = proficiency score 0–100
     */
    private Map<String, Integer> proficiencyByCategory;

    private UUID recommendedProblemId;
    private String recommendedProblemTitle;
    private ProblemDifficulty recommendedDifficulty;
    private String targetCategoryName;

    public QuestionnaireResultDTO() {}

    public QuestionnaireResultDTO(Map<String, Integer> proficiencyByCategory,
                                  UUID recommendedProblemId,
                                  String recommendedProblemTitle,
                                  ProblemDifficulty recommendedDifficulty,
                                  String targetCategoryName) {
        this.proficiencyByCategory = proficiencyByCategory;
        this.recommendedProblemId = recommendedProblemId;
        this.recommendedProblemTitle = recommendedProblemTitle;
        this.recommendedDifficulty = recommendedDifficulty;
        this.targetCategoryName = targetCategoryName;
    }

    public Map<String, Integer> getProficiencyByCategory() { return proficiencyByCategory; }
    public void setProficiencyByCategory(Map<String, Integer> proficiencyByCategory) { this.proficiencyByCategory = proficiencyByCategory; }

    public UUID getRecommendedProblemId() { return recommendedProblemId; }
    public void setRecommendedProblemId(UUID recommendedProblemId) { this.recommendedProblemId = recommendedProblemId; }

    public String getRecommendedProblemTitle() { return recommendedProblemTitle; }
    public void setRecommendedProblemTitle(String recommendedProblemTitle) { this.recommendedProblemTitle = recommendedProblemTitle; }

    public ProblemDifficulty getRecommendedDifficulty() { return recommendedDifficulty; }
    public void setRecommendedDifficulty(ProblemDifficulty recommendedDifficulty) { this.recommendedDifficulty = recommendedDifficulty; }

    public String getTargetCategoryName() { return targetCategoryName; }
    public void setTargetCategoryName(String targetCategoryName) { this.targetCategoryName = targetCategoryName; }
}
