package com.BTA.SmartPrep.repository;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface ProficiencyRepository extends JpaRepository<Proficiency, UUID> {
}
