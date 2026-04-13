package com.BTA.SmartPrep.repository;

import com.BTA.SmartPrep.domain.entity.TestCase;
import com.BTA.SmartPrep.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, UUID> {
}
