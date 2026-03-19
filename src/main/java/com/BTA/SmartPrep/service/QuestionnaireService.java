package com.BTA.SmartPrep.service;

import com.BTA.SmartPrep.domain.entity.*;
import com.BTA.SmartPrep.domain.entity.Proficiency.ProficiencyId;
import com.BTA.SmartPrep.domain.repository.*;
import com.BTA.SmartPrep.dto.QuestionnaireResultDTO;
import com.BTA.SmartPrep.dto.QuestionnaireSubmissionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {

    // -------------------------------------------------------------------------
    // Proficiency score → difficulty mapping thresholds
    //   0  – 24  →  VERY_EASY
    //   25 – 49  →  EASY
    //   50 – 74  →  MEDIUM
    //   75 – 100 →  HARD
    // -------------------------------------------------------------------------
    private static final int EASY_THRESHOLD   = 25;
    private static final int MEDIUM_THRESHOLD = 50;
    private static final int HARD_THRESHOLD   = 75;

    private final QuestionRepository    questionRepository;
    private final UserRepository        userRepository;
    private final CategoryRepository    categoryRepository;
    private final ProblemRepository     problemRepository;
    private final ProficiencyRepository proficiencyRepository;

    public QuestionnaireService(QuestionRepository questionRepository,
                                UserRepository userRepository,
                                CategoryRepository categoryRepository,
                                ProblemRepository problemRepository,
                                ProficiencyRepository proficiencyRepository) {
        this.questionRepository    = questionRepository;
        this.userRepository        = userRepository;
        this.categoryRepository    = categoryRepository;
        this.problemRepository     = problemRepository;
        this.proficiencyRepository = proficiencyRepository;
    }

    // =========================================================================
    // PUBLIC API
    // =========================================================================

    /**
     * Returns all questionnaire questions from the database.
     * The frontend displays these to the user on first login / signup.
     */
    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    /**
     * Main entry point for the recommendation engine.
     *
     * Steps:
     *  1. Score the user's answers per category
     *  2. Normalize each category score to 0–100
     *  3. Persist a Proficiency record per category
     *  4. Map each proficiency score → ProblemDifficulty
     *  5. Identify the user's weakest category (lowest proficiency)
     *  6. Find and return the best first problem for that category + difficulty
     *
     * @param submission  the user's questionnaire answers
     * @return            proficiency breakdown + recommended first problem
     */
    @Transactional
    public QuestionnaireResultDTO processAndRecommend(QuestionnaireSubmissionDTO submission) {
        User user = userRepository.findById(submission.getUserId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + submission.getUserId()));

        // --- Step 1 & 2: Score and normalize per category ---
        Map<Category, Integer> proficiencyScores = scoreAnswers(submission.getAnswers());

        // --- Step 3: Persist proficiency records ---
        saveProficiencies(user, proficiencyScores);

        // --- Step 4: Map scores → human-readable output ---
        Map<String, Integer> proficiencyByCategory = proficiencyScores.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey().getName(),
                        Map.Entry::getValue
                ));

        // --- Step 5: Find the weakest category ---
        Category weakestCategory = findWeakestCategory(proficiencyScores);

        // --- Step 6: Recommend a problem ---
        int weakestScore = proficiencyScores.get(weakestCategory);
        ProblemDifficulty targetDifficulty = mapScoreToDifficulty(weakestScore);

        Problem recommended = recommendProblem(weakestCategory, targetDifficulty);

        return new QuestionnaireResultDTO(
                proficiencyByCategory,
                recommended.getProblemId(),
                recommended.getTitle(),
                targetDifficulty,
                weakestCategory.getName()
        );
    }

    /**
     * Recommends the next problem for a user who has already completed the questionnaire.
     * Uses their current proficiency scores (already stored) to pick their next challenge.
     *
     * Strategy: target the weakest category, one difficulty step above current level
     *           to keep the user in a productive learning zone.
     *
     * @param userId  the user to generate a recommendation for
     * @return        the recommended next problem
     */
    public Problem recommendNextProblem(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        List<Proficiency> proficiencies = proficiencyRepository.findAllByUserId(userId);
        if (proficiencies.isEmpty()) {
            throw new IllegalStateException(
                    "User " + userId + " has no proficiency data. Complete the questionnaire first.");
        }

        // Find the proficiency record with the lowest score
        Proficiency weakest = proficiencies.stream()
                .min(Comparator.comparingInt(Proficiency::getProficiency))
                .orElseThrow();

        Category weakestCategory = categoryRepository
                .findById(weakest.getId().getCategoryId())
                .orElseThrow();

        // Push one step harder than current level to encourage growth
        ProblemDifficulty targetDifficulty = mapScoreToDifficulty(
                Math.min(weakest.getProficiency() + 10, 100) // nudge score up slightly
        );

        return recommendProblem(weakestCategory, targetDifficulty);
    }

    // =========================================================================
    // PRIVATE HELPERS
    // =========================================================================

    /**
     * Scores user answers against the question bank.
     *
     * For each question:
     *  - If answered correctly → add pointValue to that category's raw score
     *  - Track the max possible score per category (sum of all pointValues)
     *
     * Then normalize: finalScore = (rawScore / maxPossible) * 100
     */
    private Map<Category, Integer> scoreAnswers(Map<UUID, String> answers) {
        List<Question> questions = questionRepository.findAll();

        // Raw earned points per category
        Map<Category, Integer> rawScores = new HashMap<>();
        // Max possible points per category
        Map<Category, Integer> maxScores = new HashMap<>();

        for (Question question : questions) {
            Category cat = question.getCategory();
            maxScores.merge(cat, question.getPointValue(), Integer::sum);

            String userAnswer = answers.get(question.getQuestionId());
            boolean correct = userAnswer != null &&
                              userAnswer.equalsIgnoreCase(question.getCorrectAnswer());

            if (correct) {
                rawScores.merge(cat, question.getPointValue(), Integer::sum);
            } else {
                rawScores.putIfAbsent(cat, 0);
            }
        }

        // Normalize each category to 0–100
        Map<Category, Integer> normalized = new HashMap<>();
        for (Category cat : maxScores.keySet()) {
            int raw = rawScores.getOrDefault(cat, 0);
            int max = maxScores.get(cat);
            int score = (max == 0) ? 0 : (int) Math.round((raw * 100.0) / max);
            normalized.put(cat, score);
        }

        return normalized;
    }

    /**
     * Persists (or updates) one Proficiency row per category for the user.
     */
    private void saveProficiencies(User user, Map<Category, Integer> proficiencyScores) {
        for (Map.Entry<Category, Integer> entry : proficiencyScores.entrySet()) {
            ProficiencyId id = new ProficiencyId(user.getId(), entry.getKey().getCategoryId());
            Proficiency proficiency = proficiencyRepository.findById(id)
                    .orElse(new Proficiency(id, 0));
            proficiency.setProficiency(entry.getValue());
            proficiencyRepository.save(proficiency);
        }
    }

    /**
     * Returns the category with the lowest proficiency score.
     * This is the area the user needs the most help with — the target for recommendation.
     */
    private Category findWeakestCategory(Map<Category, Integer> proficiencyScores) {
        return proficiencyScores.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("No categories found in questionnaire."));
    }

    /**
     * Maps a 0–100 proficiency score to a ProblemDifficulty enum.
     *
     *  0–24   → VERY_EASY
     *  25–49  → EASY
     *  50–74  → MEDIUM
     *  75–100 → HARD
     */
    private ProblemDifficulty mapScoreToDifficulty(int score) {
        if (score < EASY_THRESHOLD)   return ProblemDifficulty.VERY_EASY;
        if (score < MEDIUM_THRESHOLD) return ProblemDifficulty.EASY;
        if (score < HARD_THRESHOLD)   return ProblemDifficulty.MEDIUM;
        return ProblemDifficulty.HARD;
    }

    /**
     * Finds the best problem to recommend given a category and difficulty.
     *
     * Strategy:
     *  1. Try to find a problem at exactly the target category + difficulty.
     *  2. If none exist, fall back to any problem in that category.
     *  3. If that category has no problems at all, fall back to any problem at that difficulty.
     *  4. Last resort: return any available problem.
     *
     * Returns the first match (problems are unordered at this stage — ordering
     * by attempts/history can be added in a future iteration).
     */
    private Problem recommendProblem(Category category, ProblemDifficulty difficulty) {
        // 1. Exact match
        List<Problem> exact = problemRepository.findByCategoryAndProblemDifficulty(category, difficulty);
        if (!exact.isEmpty()) return exact.get(0);

        // 2. Same category, any difficulty
        List<Problem> byCategory = problemRepository.findByCategory(category);
        if (!byCategory.isEmpty()) return byCategory.get(0);

        // 3. Same difficulty, any category
        List<Problem> byDifficulty = problemRepository.findByProblemDifficulty(difficulty);
        if (!byDifficulty.isEmpty()) return byDifficulty.get(0);

        // 4. Absolute fallback
        return problemRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No problems exist in the database. Please seed some problems first."));
    }
}
