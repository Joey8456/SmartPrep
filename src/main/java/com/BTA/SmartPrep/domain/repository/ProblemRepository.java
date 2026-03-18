package com.BTA.SmartPrep.domain.repository;

import com.BTA.SmartPrep.domain.entity.Category;
import com.BTA.SmartPrep.domain.entity.Problem;
import com.BTA.SmartPrep.domain.entity.ProblemDifficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, UUID> {

    List<Problem> findByCategory(Category category);

    List<Problem> findByProblemDifficulty(ProblemDifficulty difficulty);

    /**
     * Key query for the recommendation engine:
     * Find problems matching a specific category AND difficulty level.
     */
    List<Problem> findByCategoryAndProblemDifficulty(Category category, ProblemDifficulty difficulty);
}
