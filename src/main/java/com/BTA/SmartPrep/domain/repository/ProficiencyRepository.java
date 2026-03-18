package com.BTA.SmartPrep.domain.repository;

import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.Proficiency.ProficiencyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProficiencyRepository extends JpaRepository<Proficiency, ProficiencyId> {

    /**
     * Get all proficiency scores for a given user across all categories.
     */
    @Query("SELECT p FROM Proficiency p WHERE p.id.userId = :userId")
    List<Proficiency> findAllByUserId(@Param("userId") UUID userId);
}
