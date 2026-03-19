package com.BTA.SmartPrep.repository;

import com.BTA.SmartPrep.domain.entity.Proficiency;
import com.BTA.SmartPrep.domain.entity.Proficiency.ProficiencyId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepository extends JpaRepository<Proficiency, ProficiencyId> {
}