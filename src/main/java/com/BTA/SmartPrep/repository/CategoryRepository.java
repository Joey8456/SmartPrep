package com.BTA.SmartPrep.repository;
import com.BTA.SmartPrep.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    // In CategoryRepository.java — add this method:
    Optional<Category> findByName(String categoryName);
}
