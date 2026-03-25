package com.BTA.SmartPrep.repository;
import com.BTA.SmartPrep.domain.entity.Proficiency;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProficiencyRepository extends JpaRepository<Proficiency, Proficiency.ProficiencyId> {
    @Modifying
    @Transactional
    @Query(value = """
        INSERT INTO Proficiencies (user_id, category_id, proficiency)
        VALUES (:userId, :categoryId, :proficiency)
        """, nativeQuery = true)
    void insertProficiency(
            @Param("userId") String userId,
            @Param("categoryId") int categoryId,
            @Param("proficiency") int proficiency
    );
}
