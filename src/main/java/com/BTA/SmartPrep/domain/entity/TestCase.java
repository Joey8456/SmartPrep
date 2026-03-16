package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "Test_Cases")
public class TestCase {
    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "submission_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID testId;

    @Column(name = "testCase")
    private String testCase;

    @Column(name = "expected_Out")
    private String expectedOutput;

    @ManyToOne
    @JoinColumn(name = "problem_ID")
    private Problem problem;

}
