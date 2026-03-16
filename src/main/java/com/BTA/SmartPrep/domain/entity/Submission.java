package com.BTA.SmartPrep.domain.entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "Submissions")
public class Submission {
    @Id //This Annotation Says this is an ID
    @GeneratedValue(strategy = GenerationType.UUID) //Creates ID for us, using UUID
    @Column(name = "submission_ID",updatable = false,nullable = false) //col name from DB, not Updatable, cannot be null
    private UUID submissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rating")
    private SolutionRating solutionRating;

    @CreationTimestamp
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    @Column(name = "answer")
    private String answer;

    @ManyToOne
    @JoinColumn(name = "user_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "problem_ID")
    private Problem problem;

}
