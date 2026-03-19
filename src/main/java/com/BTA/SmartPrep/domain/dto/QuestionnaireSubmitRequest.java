package com.BTA.SmartPrep.domain.dto;

import java.util.Map;

/**
 * Matches exactly what the frontend already sends via responses:
 *   experienceLevel  → "Beginner" | "Intermediate" | "Experienced"
 *   topicRatings     → { "arrays_strings": 3, "two_pointers": 1, "hash_maps_sets": 2 }
 */
public record QuestionnaireSubmitRequest(
        String userId,
        String experienceLevel,
        Map<String, Integer> topicRatings   // values are 1–5
) {}