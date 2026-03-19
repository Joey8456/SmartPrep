package com.BTA.SmartPrep.service.impl;

import com.BTA.SmartPrep.domain.dto.QuestionnaireSubmitRequest;
import com.BTA.SmartPrep.domain.entity.Category;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.Proficiency.ProficiencyId;
import com.BTA.SmartPrep.repository.CategoryRepository;
import com.BTA.SmartPrep.repository.ProficiencyRepository;
import com.BTA.SmartPrep.service.QuestionnaireService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

    // Maps the frontend's topic keys → the exact category names in your DB
    private static final Map<String, String> TOPIC_TO_CATEGORY = Map.of(
            "arrays_strings",  "Arrays & Strings",
            "two_pointers",    "Two Pointers",
            "hash_maps_sets",  "Hash Maps / Sets"
    );

    // Maps experience level answer → starting proficiency score
    private static final Map<String, Integer> EXPERIENCE_TO_SCORE = Map.of(
            "Beginner",     20,
            "Intermediate", 50,
            "Experienced",  80
    );

    private final ProficiencyRepository proficiencyRepository;
    private final CategoryRepository    categoryRepository;

    public QuestionnaireServiceImpl(ProficiencyRepository proficiencyRepository,
                                    CategoryRepository categoryRepository) {
        this.proficiencyRepository = proficiencyRepository;
        this.categoryRepository    = categoryRepository;
    }

    @Override
    @Transactional
    public void submitQuestionnaire(QuestionnaireSubmitRequest request) {
        UUID userUUID = UUID.fromString(request.userId());

        // --- Q1: experience level sets a baseline across all categories ---
        int baseline = EXPERIENCE_TO_SCORE.getOrDefault(request.experienceLevel(), 20);

        // --- Q2–Q4: each rating (1–5) overrides that category's proficiency ---
        // rating × 20 converts 1–5 scale → 0–100
        for (Map.Entry<String, Integer> entry : request.topicRatings().entrySet()) {
            String topicKey  = entry.getKey();
            int    rating    = entry.getValue();             // 1–5 from the UI

            String categoryName = TOPIC_TO_CATEGORY.get(topicKey);
            if (categoryName == null) continue;              // ignore unknown keys

            Category category = categoryRepository
                    .findByName(categoryName)
                    .orElse(null);
            if (category == null) continue;                  // category not seeded yet

            int score = clamp(rating * 20);                  // 1→20, 2→40 ... 5→100

            upsertProficiency(userUUID, category.getCategoryId(), score);
        }

        // --- Save baseline for any category the user didn't explicitly rate ---
        categoryRepository.findAll().forEach(category -> {
            ProficiencyId id = new ProficiencyId(userUUID, category.getCategoryId());
            boolean alreadySet = proficiencyRepository.existsById(id);
            if (!alreadySet) {
                proficiencyRepository.save(new Proficiency(id, baseline));
            }
        });
    }

    // -------------------------------------------------------------------------

    private void upsertProficiency(UUID userId, UUID categoryId, int score) {
        ProficiencyId id = new ProficiencyId(userId, categoryId);
        Proficiency proficiency = proficiencyRepository.findById(id)
                .orElse(new Proficiency(id, 0));
        proficiency.setProficiency(score);
        proficiencyRepository.save(proficiency);
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(100, value));
    }
}