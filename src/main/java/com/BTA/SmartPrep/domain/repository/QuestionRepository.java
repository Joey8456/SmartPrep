package com.BTA.SmartPrep.domain.repository;

import com.BTA.SmartPrep.domain.entity.Category;
import com.BTA.SmartPrep.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {

    List<Question> findByCategory(Category category);
}
