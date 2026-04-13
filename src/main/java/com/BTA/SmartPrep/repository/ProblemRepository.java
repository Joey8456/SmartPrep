package com.BTA.SmartPrep.repository;
import com.BTA.SmartPrep.domain.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = """
        SELECT *
        FROM Problems
        WHERE category_id = :categoryId AND difficulty = :difficulty
        ORDER BY RAND()
        LIMIT 1
        """, nativeQuery = true)
    Optional<Problem> findRandomByCategoryId(@Param("categoryId") int categoryId, @Param("difficulty") String difficulty);
}
