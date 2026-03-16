package com.BTA.SmartPrep.domain.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.ManyToAny;

import java.util.UUID;

@Entity
@Table(name="Problems")
public class Problem {
    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "problem_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID problemId;

    @Column(name = "pTitle")
    private String title;

    @Column(name = "prompt")
    private String prompt;

    @Column(name = "examples")
    private String examples;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty", nullable = false)
    private ProblemDifficulty problemDifficulty;

    @ManyToOne
    @JoinColumn(name = "category_ID")
    private Category category;
}
